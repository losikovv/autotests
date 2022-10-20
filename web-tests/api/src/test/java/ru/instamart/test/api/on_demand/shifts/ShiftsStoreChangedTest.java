package ru.instamart.test.api.on_demand.shifts;

import candidates.StoreChangedOuterClass;
import com.google.protobuf.UInt32Value;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.jdbc.dao.shifts.PlanningAreasDao;
import ru.instamart.jdbc.dao.shifts.PlanningPeriodsDao;
import ru.instamart.jdbc.dao.shifts.ShopsDao;
import ru.instamart.jdbc.dto.shifts.PlanningPeriodFilters;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodEntity;
import ru.sbermarket.qase.annotation.CaseId;
import shifts.ImportPlanningPeriods;

import java.util.*;

import static org.testng.Assert.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configPlanningPeriods;
import static ru.instamart.kafka.configs.KafkaConfigs.configStoreChanged;
import static ru.instamart.kafka.enums.Pods.SHIFT_SERVICE_CONSUMER;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;
import static ru.instamart.kraken.util.TimeUtil.*;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsStoreChangedTest extends RestBase {

    private Integer baseStoreId;
    private Integer deliveryAreaId;
    private final String storeUUID = UUID.randomUUID().toString(); //"4872ead0-274b-49a2-955e-a5101a7de9cb";
    private String date = getFutureDateWithoutTime(1L);
    private String dateTime = getZonedDate();
    private List<PlanningPeriodEntity> planningPeriodsBefore;

    @BeforeClass(alwaysRun = true,
            description = "Отправка информации о создании магазина через кафку")
    public void preconditions() {
        var shopsEntity = ShopsDao.INSTANCE.getOriginalId();
        assertTrue(shopsEntity.size() > 0, "Данные по id shops пустые");
        baseStoreId = shopsEntity.get(0).getOriginalId() + 1;
        deliveryAreaId = baseStoreId + 1;
        var storeChanged = StoreChangedOuterClass.StoreChanged.newBuilder()
                .setId(baseStoreId)
                .setBaseStoreId(baseStoreId)
                .setUuid(storeUUID)
                .setName("METRO, Тестовая_" + baseStoreId)
                .setCreatedAt(getTimestamp())
                .setUpdatedAt(getTimestamp())
                .setTimeZone("Europe/Moscow")
                .setOperationalZoneId(1)
                .setRetailerId(1)
                .setImportKeyPostfix(String.valueOf(RandomUtils.nextInt(1, 999)))
                .setLocation("{\"id\":95,\"lat\":55.630794,\"lon\":37.624798,\"area\":null,\"city\":\"Москва\",\"kind\":null,\"block\":\"\",\"floor\":null,\"phone\":null,\"region\":null,\"street\":\"Дорожная\",\"building\":\" д. 1, корп. 1\",\"comments\":null,\"entrance\":null,\"apartment\":null,\"door_phone\":null,\"settlement\":null,\"elevator\":null,\"delivery_to_door\":false,\"full_address\":\"Москва, Дорожная,  д. 1, корп. 1\"}")
                .setHelpdeskeddyId(RandomUtils.nextInt(100, 999))
                .setAutoRouting(true)
                .setSecondsForAssemblyItem(RandomUtils.nextInt(1, 999))
                .setAdditionalSecondsForAssembly(RandomUtils.nextLong(1000, 9999))
                .setDeliveryAreaId(deliveryAreaId)
                .setRetailerStoreId("12")
                .setBoxScanning(true)
                .setScheduleType("dispatch")
                .setStoreZones("[{\"id\":70,\"name\":\"Зона Дорожная\",\"area\":[[[37.6346352,55.684103000000015],[37.628947,55.70151000000001],[37.629633,55.70518400000001],[37.623694,55.70585499999999],[37.621737,55.705837],[37.619092,55.705325999999985],[37.6186622,55.705132000000006],[37.6081052,55.701553],[37.5888792,55.70682500000001],[37.5831292,55.70970210000001],[37.5735422,55.687786],[37.5082692,55.62179200000001],[37.4911022,55.6108373],[37.5087839,55.596194999999994],[37.5686932,55.58125600000001],[37.5535872,55.56960999999999],[37.5533372,55.557857000000006],[37.5831122,55.55038800000001],[37.5999352,55.55932099999999],[37.6030252,55.56534],[37.5966742,55.576113],[37.6706602,55.572328],[37.6880842,55.575637],[37.68037819999999,55.58318299999999],[37.668919,55.60412000000001],[37.662927,55.60253199999999],[37.6578712,55.602014],[37.6494072,55.602391999999995],[37.64135420000001,55.610046],[37.6359602,55.613774000000014],[37.6329252,55.61867899999999],[37.6286602,55.62777100000002],[37.6257712,55.63855699999999],[37.657571,55.651055],[37.6705744,55.65463840000001],[37.672806,55.66441899999999],[37.676239,55.66829100000001],[37.685913,55.670838],[37.695009,55.67196579999999],[37.7030751,55.67096320000001],[37.709593000000005,55.666827700000006],[37.7142177,55.6683341],[37.71454210000001,55.67231490000002],[37.7109019,55.68017930000001],[37.7125856,55.68495279999999],[37.70634,55.688694],[37.695611,55.69145100000001],[37.6852854,55.690934199999994],[37.6657497,55.68637699999999],[37.6453916,55.6846066],[37.6346352,55.684103000000015]]]}]")
                .setAvailableOn(getTimestamp())
                .setOpeningTime("10:00")
                .setClosingTime("20:00")
                .build();
        kafka.publish(configStoreChanged(), storeChanged);
    }

    @AfterClass(alwaysRun = true, description = "Clear")
    public void after() {
        ShopsDao.INSTANCE.delete(baseStoreId);
    }

    @CaseId(177)
    @Story("Создание зон планирования из стрима магазинов")
    @Test(groups = {"api-shifts"},
            description = "Создание зоны планирования для магазина с быстрой доставкой и территорией доставки")
    public void createStore() {
        var planningAreas = PlanningAreasDao.INSTANCE.getPlanningAreas(deliveryAreaId);
        Allure.step("Asserts", () ->
                assertEquals(planningAreas.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }

    @CaseId(178)
    @Story("Создание зон планирования из стрима магазинов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "createStore",
            description = "Создание зоны планирования для магазина с быстрой доставкой и территорией доставки")
    public void createStore2() {
        var storeChanged = StoreChangedOuterClass.StoreChanged.newBuilder()
                .setId(baseStoreId)
                .setUuid(storeUUID)
                .setName("Ашан, Севастопольский просп.")
                .setCreatedAt(getTimestamp())
                .setUpdatedAt(getTimestamp())
                .setTimeZone("Europe/Moscow")
                .setOperationalZoneId(1)
                .setRetailerId(15)
                .setImportKeyPostfix("1")
                .setLocation("{\"id\":171,\"lat\":55.686856,\"lon\":37.603826,\"area\":null,\"city\":\"Москва\",\"kind\":null,\"block\":null,\"floor\":null,\"phone\":null,\"region\":null,\"street\":\"Севастопольский просп.\",\"building\":\"11Е\",\"comments\":null,\"entrance\":null,\"apartment\":null,\"door_phone\":null,\"settlement\":null,\"elevator\":null,\"delivery_to_door\":false,\"full_address\":\"Москва, Севастопольский просп., 11Е\"}")
                .setHelpdeskeddyId(1293)
                .setFastPaymentMetroStoreDns("MOW11MPSU010001")
                .setFastPaymentMetroBarcodeCiphertext("OCTZ3Td/m0q3TPPKOBgcP+7oo78S5b22IQVokP7KHw6flEiaQbvC7UWOhKgvTin2hUI=")
                .setExpressDelivery(true)
                .setBaseStoreId(baseStoreId)
                .setDeliveryAreaId(deliveryAreaId)
                .setRetailerStoreId("-1")
                .setScheduleType("list")
                .setStoreZones("[{\"id\":47,\"area\":[[[37.6387751,56.0294056],[37.3970759,55.8679255],[37.0070613,55.8771711],[37.2597468,55.7042236],[37.0043147,55.5631378],[37.3696101,55.564691],[37.5975764,55.3981549],[37.7870906,55.5476029],[38.1963313,55.5476029],[37.8887141,55.7181496],[38.2265437,55.8679255],[37.8530085,55.8710076],[37.6387751,56.0294056]]],\"name\":\"Москва TEST\"}]")
                .setAvailableOn(getTimestamp())
                .build();
        kafka.publish(configStoreChanged(), storeChanged);

        var planningAreas = PlanningAreasDao.INSTANCE.getPlanningAreas(deliveryAreaId);
        Allure.step("Asserts", () ->
                assertEquals(planningAreas.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }


    @CaseId(81)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "importPlanningPeriodForUniversalAutoUniversal",
            description = "Добавление плановых периодов используя идентификатор зоны доставки")
    public void importPlanningPeriod200() {
        var importIdBefore = "KRAKEN_IMPORT" + RandomUtils.nextLong(1000000L, 9999999L);
        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(importIdBefore)
                .setSentAt(dateTime)
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T11:00:00+00:00")
                                .setEndedAt(date + "T12:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T12:00:00+00:00")
                                .setEndedAt(date + "T13:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .build();

        kafka.publish(configPlanningPeriods(), periodsImport);
        simplyAwait(10);
        var filters = PlanningPeriodFilters.builder()
                .importId(importIdBefore)
                .build();
        planningPeriodsBefore = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);

        Allure.step("Проверяем импорт", () ->
                assertTrue(planningPeriodsBefore.size() > 0, "Импорт в БД пустой")
        );
    }

    @CaseId(81)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "importPlanningPeriod200",
            description = "Обновление существующих плановых периодов")
    public void updatePlanningPeriod200() {
        String importId = "KRAKEN_UPDATE_" + RandomUtils.nextLong(1000000L, 9999999L);
        var filters2 = PlanningPeriodFilters.builder()
                .importId(importId)
                .build();
        var periodsImportStep2 = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(importId)
                .setSentAt(dateTime)
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T11:00:00+00:00")
                                .setEndedAt(date + "T12:00:00+00:00")
                                .setPeoplesCount(10)
                                .setPeoplesCountPredicted(12)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 201F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 201F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(23).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T12:00:00+00:00")
                                .setEndedAt(date + "T13:00:00+00:00")
                                .setPeoplesCount(10)
                                .setPeoplesCountPredicted(12)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 201F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 201F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(23).build())
                                .build()
                )
                .build();
        kafka.publish(configPlanningPeriods(), periodsImportStep2);
        simplyAwait(10);
        var planningPeriodsAfter = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters2);

        Allure.step("", ()->{
            assertNotEquals(planningPeriodsBefore.get(0).getGuaranteedPayroll(), planningPeriodsAfter.get(0).getGuaranteedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(1).getGuaranteedPayroll(), planningPeriodsAfter.get(1).getGuaranteedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(0).getPredictedPayroll(), planningPeriodsAfter.get(0).getPredictedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(1).getPredictedPayroll(), planningPeriodsAfter.get(1).getPredictedPayroll(), "Данные совпадают");
        });
    }

    @CaseId(136)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            description = "Добавление плановых периодов для всех существующих ролей")
    public void addPlanningPeriodForAllRole() {
        var randomId = "kraken_" + RandomUtils.nextLong(1000000L, 9999999L);

        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(randomId)
                .setSentAt(dateTime)
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.SHOPPER.getRole())
                                .setStartedAt(date + "T13:00:00+00:00")
                                .setEndedAt(date + "T14:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.SHOPPER.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.DRIVER.getRole())
                                .setStartedAt(date + "T14:00:00+00:00")
                                .setEndedAt(date + "T15:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.DRIVER.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.DRIVER.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T15:00:00+00:00")
                                .setEndedAt(date + "T16:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.UNIVERSAL.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.UNIVERSAL.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.AUTO_UNIVERSAL.getRole())
                                .setStartedAt(date + "T16:00:00+00:00")
                                .setEndedAt(date + "T17:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.AUTO_UNIVERSAL.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.AUTO_UNIVERSAL.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.PACKER.getRole())
                                .setStartedAt(date + "T17:00:00+00:00")
                                .setEndedAt(date + "T18:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.PACKER.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.PACKER.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .addPlanningPeriods(
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setStoreId(baseStoreId)
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.EXTERNAL_INSPECTOR.getRole())
                                .setStartedAt(date + "T18:00:00+00:00")
                                .setEndedAt(date + "T19:00:00+00:00")
                                .setPeoplesCount(7)
                                .setPeoplesCountPredicted(9)
                                .putAllGuaranteedPayroll(Map.of(RoleSHP.EXTERNAL_INSPECTOR.getRole(), 200F))
                                .putAllPredictedPayroll(Map.of(RoleSHP.EXTERNAL_INSPECTOR.getRole(), 200F))
                                .setIsActive(true)
                                .setSurged(true)
                                .setMaxPeoplesCount(UInt32Value.newBuilder().setValue(16).build())
                                .build()
                )
                .build();
        kafka.publish(configPlanningPeriods(), periodsImport);
        simplyAwait(10);
        var filters = PlanningPeriodFilters.builder()
                .importId(randomId)
                .build();
        var planningPeriods = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);
        Allure.step("Asserts", () ->
                assertEquals(planningPeriods.size(), 6, "Данные shops пустые или вернулось более одного магазина")
        );
    }

    @CaseId(204)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "createStore",
            description = "Импорт ПП для роли universal/auto_universal (новый формат)")
    public void importPlanningPeriodForUniversalAutoUniversal() {
        var importId = "KRAKEN_IMPORT" + RandomUtils.nextLong(1000000L, 9999999L);
        Map<String, Float> payroll = new HashMap<>();
        payroll.put("universal", 100F);
        payroll.put("auto_universal", 200F);
        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(importId)
                .setSentAt(date + "T12:00:00+03:00")
                .addPlanningPeriods(0,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setStoreId(16)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T07:00:00+00:00")
                                .setEndedAt(date + "T08:00:00+00:00")
                                .setPeoplesCount(1)
                                .putAllGuaranteedPayroll(payroll)
                                .putAllPredictedPayroll(payroll)
                                .setIsActive(true)
                                .setSurged(true)
                                .build()
                )
                .build();

        kafka.publish(configPlanningPeriods(), periodsImport);
        simplyAwait(10);
        var filters = PlanningPeriodFilters.builder()
                .importId(importId)
                .build();
        planningPeriodsBefore = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);
        Allure.step("Asserts", () ->
                assertTrue(planningPeriodsBefore.size() > 0, "Импорт в БД пустой")
        );
    }
}
