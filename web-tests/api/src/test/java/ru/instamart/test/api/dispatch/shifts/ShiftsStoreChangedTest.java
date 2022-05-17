package ru.instamart.test.api.dispatch.shifts;

import candidates.StoreChangedOuterClass;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.jdbc.dao.shifts.PlanningAreasDao;
import ru.instamart.jdbc.dao.shifts.PlanningPeriodsDao;
import ru.instamart.jdbc.dao.shifts.ShopsDao;
import ru.instamart.jdbc.dto.shifts.PlanningPeriodFilters;
import ru.instamart.kafka.enums.Pods;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;
import shifts.ImportPlanningPeriods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configPlanningPeriods;
import static ru.instamart.kafka.configs.KafkaConfigs.configStoreChanged;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsStoreChangedTest extends RestBase {

    private Integer baseStoreId;
    private Integer deliveryAreaId;
    private String storeUUID = UUID.randomUUID().toString();
    private String date = getDateFromMSK();

    @BeforeClass(alwaysRun = true,
            description = "Отправка информации о создании магазина через кафку")
    public void preconditions() {
        var shopsEntity = ShopsDao.INSTANCE.getOriginalId();
        assertTrue(shopsEntity.size() > 0, "Данные по id shops пустые");
        baseStoreId = shopsEntity.get(0).getOriginalId() + 1;
        deliveryAreaId = baseStoreId + 1;
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
        kafka.publish(configStoreChanged(), storeChanged.toByteArray());
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
        kafka.publish(configStoreChanged(), storeChanged.toByteArray());

        var planningAreas = PlanningAreasDao.INSTANCE.getPlanningAreas(deliveryAreaId);
        Allure.step("Asserts", () ->
                assertEquals(planningAreas.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }


    @CaseId(81)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "createStore",
            description = "Добавление плановых периодов используя идентификатор зоны доставки")
    public void importPlanningPeriod200() {
        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(String.valueOf(RandomUtils.nextLong(1000000L, 9999999L)))
                .setSentAt(date + "T12:00:00+03:00")
                .setPlanningPeriods(0,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T11:00:00+00:00")
                                .setEndedAt(date + "T12:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .setPlanningPeriods(1,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T12:00:00+00:00")
                                .setEndedAt(date + "T13:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .build();

        kafka.publish(configPlanningPeriods(), periodsImport.toByteArray());
        var pod = Pods.stream()
                .filter(item -> item.getNameSpace() == EnvironmentProperties.SERVICE)
                .findFirst().get();
        List<String> logsPods = kubeLog.getLogsPods(pod.getNameSpace(), pod.getLabel(), "\"planning_area_id\": " + deliveryAreaId);
        Allure.step("Asserts", () ->
                assertTrue(logsPods.size() > 0, "Вернулись пустые логи")
        );
    }

    @CaseId(81)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "importPlanningPeriod200",
            description = "Обновление существующих плановых периодов")
    public void updatePlanningPeriod200() {
        var filters = PlanningPeriodFilters.builder()
                .planningAreaId(deliveryAreaId)
                .role(RoleSHP.UNIVERSAL.getRole())
                .build();
        var planningPeriodsBefore = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);

        var periodsImportStep2 = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(String.valueOf(RandomUtils.nextLong(1000000L, 9999999L)))
                .setSentAt(date + "T12:00:00+03:00")
                .addPlanningPeriods(0,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T11:00:00+00:00")
                                .setEndedAt(date + "T12:00:00+00:00")
                                .setPeoplesCount(10)
                                .setBaseGuaranteedPayroll(201)
                                .setBasePredictedPayroll(201)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(1,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T12:00:00+00:00")
                                .setEndedAt(date + "T13:00:00+00:00")
                                .setPeoplesCount(10)
                                .setBaseGuaranteedPayroll(201)
                                .setBasePredictedPayroll(201)
                                .setIsActive(true)
                                .build()
                )
                .build();

        kafka.publish(configPlanningPeriods(), periodsImportStep2.toByteArray());
        var planningPeriodsAfter = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);
        assertNotEquals(planningPeriodsBefore, planningPeriodsAfter, "Данные совпадают");
    }

    @CaseId(136)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            description = "Добавление плановых периодов для всех существующих ролей")
    public void addPlanningPeriodForAllRole() {
        var randomId = String.valueOf(RandomUtils.nextLong(1000000L, 9999999L));
        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(randomId)
                .setSentAt(date + "T12:00:00+03:00")
                .addPlanningPeriods(0,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.SHOPPER.getRole())
                                .setStartedAt(date + "T13:00:00+00:00")
                                .setEndedAt(date + "T14:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(1,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.DRIVER.getRole())
                                .setStartedAt(date + "T14:00:00+00:00")
                                .setEndedAt(date + "T15:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(2,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.UNIVERSAL.getRole())
                                .setStartedAt(date + "T15:00:00+00:00")
                                .setEndedAt(date + "T16:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(3,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.AUTO_UNIVERSAL.getRole())
                                .setStartedAt(date + "T16:00:00+00:00")
                                .setEndedAt(date + "T17:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(4,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.PACKER.getRole())
                                .setStartedAt(date + "T17:00:00+00:00")
                                .setEndedAt(date + "T18:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .addPlanningPeriods(5,
                        ImportPlanningPeriods.PlanningPeriodItem.newBuilder()
                                .setPlanningAreaId(deliveryAreaId)
                                .setRole(RoleSHP.EXTERNAL_INSPECTOR.getRole())
                                .setStartedAt(date + "T18:00:00+00:00")
                                .setEndedAt(date + "T19:00:00+00:00")
                                .setPeoplesCount(7)
                                .setBaseGuaranteedPayroll(200)
                                .setBasePredictedPayroll(200)
                                .setIsActive(true)
                                .build()
                )
                .build();

        kafka.publish(configPlanningPeriods(), periodsImport.toByteArray());
        var filters = PlanningPeriodFilters.builder()
                .importId(randomId)
                .build();
        var planningPeriods = PlanningPeriodsDao.INSTANCE.getPlanningPeriods(filters);
        Allure.step("Asserts", () ->
                assertEquals(planningPeriods.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }

    @CaseId(204)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "createStore",
            description = "Импорт ПП для роли universal/auto_universal (новый формат)")
    public void importPlanningPeriodForUniversalAutoUniversal() {
        Map<String, Float> payroll = new HashMap<>();
        payroll.put("universal", 100F);
        payroll.put("auto_universal", 200F);
        var periodsImport = ImportPlanningPeriods.PlaningPeriodsImport.newBuilder()
                .setId(String.valueOf(RandomUtils.nextLong(1000000L, 9999999L)))
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

        kafka.publish(configPlanningPeriods(), periodsImport.toByteArray());
        var pod = Pods.stream()
                .filter(item -> item.getNameSpace() == EnvironmentProperties.SERVICE)
                .findFirst().get();
        List<String> logsPods = kubeLog.getLogsPods(pod.getNameSpace(), pod.getLabel(), "\"planning_area_id\": " + deliveryAreaId);
        Allure.step("Asserts", () ->
                assertTrue(logsPods.size() > 0, "Вернулись пустые логи")
        );
    }
}
