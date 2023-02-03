package ru.instamart.test.api.on_demand.shifts;

import candidates.StoreChangedOuterClass;
import candidates.StoreChangedOuterClass.AssemblyType;
import candidates.StoreChangedOuterClass.AvailableTasks;
import candidates.StoreChangedOuterClass.DispatchSettings;
import candidates.StoreChangedOuterClass.PlaceSettings.DeliveryType;
import com.google.protobuf.UInt32Value;
import io.qameta.allure.*;
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
import ru.instamart.kraken.listener.Skip;
import shifts.ImportPlanningPeriods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configPlanningPeriods;
import static ru.instamart.kafka.configs.KafkaConfigs.configStoreChanged;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;
import static ru.instamart.kraken.util.TimeUtil.*;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsStoreChangedTest extends RestBase {

    private final String storeUUID = UUID.randomUUID().toString(); //"4872ead0-274b-49a2-955e-a5101a7de9cb";
    private Integer baseStoreId;
    private Integer deliveryAreaId;
    private String date = getFutureDateWithoutTime(1L);
    private String dateTime = getZonedDate();
    private List<PlanningPeriodEntity> planningPeriodsBefore;
    private String kafkaStoreUUIDKey = UUID.randomUUID().toString();

    @BeforeClass(alwaysRun = true,
            description = "Отправка информации о создании магазина через кафку")
    public void preconditions() {
        var shopsEntity = ShopsDao.INSTANCE.getOriginalId();
        assertTrue(shopsEntity.size() > 0, "Данные по id shops пустые");
        baseStoreId = shopsEntity.get(0).getOriginalId() + 1;
        deliveryAreaId = baseStoreId + 1;
        var storeChanged = StoreChangedOuterClass.StoreChanged.newBuilder()
                .setAdditionalSecondsForAssembly(600)
                .setAutoRouting(true)
                .setAvailableOn(getTimestamp())
                .setBaseStoreId(baseStoreId)
                .setClosingTime("23:00")
                .setCreatedAt(getTimestamp())
                .setDeliveryAreaId(deliveryAreaId)
                .setDispatchSettings(DispatchSettings.newBuilder()
                        .setAdditionalFactorForStraightDistanceToClientMin(5L)
                        .setAverageSpeedForStraightDistanceToClientMin(3L)
                        .setAvgParkingMinVehicle(5L)
                        .setAvgToPlaceMin(15L)
                        .setAvgToPlaceMinExternal(20L)
                        .setGapTaxiPunishMin(15L)
                        .setLastPositionExpire(180L)
                        .setMaxCurrentOrderAssignQueue(5L)
                        .setMaxOrderAssignRetryCount(5L)
                        .setMaxWaitingTimeForCourierMin(5L)
                        .setOfferSeenTimeoutSec(60L)
                        .setOfferServerTimeoutSec(60L)
                        .setOrderReceiveTimeFromAssemblyToDeliveryMin(5L)
                        .setOrderTransferTimeFromAssemblyToDeliveryMin(5L)
                        .setOrderTransferTimeFromDeliveryToClientMin(3L)
                        .setOrderWeightThresholdToAssignToVehicleGramms(10000L)
                        .build())
                .setExpressDelivery(true)
                .setHelpdeskeddyId(1234L)
                .setId(baseStoreId)
                .setImportKeyPostfix("999")
                .setLocation("{\"id\":95,\"lat\":55.630794,\"lon\":37.624798,\"area\":null,\"city\":\"Москва\",\"kind\":null,\"block\":\"\",\"floor\":null,\"phone\":null,\"region\":null,\"street\":\"Дорожная\",\"building\":\" д. 1, корп. 1\",\"comments\":null,\"entrance\":null,\"apartment\":null,\"door_phone\":null,\"settlement\":null,\"elevator\":null,\"delivery_to_door\":false,\"full_address\":\"Москва, Дорожная,  д. 1, корп. 1\"}")
                .setName("METRO, Тестовая_" + baseStoreId)
                .setOpeningTime("01:00")
                .setOperationalZoneId(1)
                .setOperationalZoneName("Москва")
                .setPlaceSettings(StoreChangedOuterClass.PlaceSettings.newBuilder()
                        .setAssemblyTaskType(AssemblyType.SM)
                        .setDeliveryTaskType(DeliveryType.SM)
                        .addPlaceAvailableTasksToBeAssigned(AvailableTasks.ASSEMBLY)
                        .addPlaceAvailableTasksToBeAssigned(AvailableTasks.DELIVERY)
                        .setPlaceLocation(StoreChangedOuterClass.LocationPoint.newBuilder()
                                .setLat(55.630794)
                                .setLon(37.624798)
                                .build())
                        .build())
                .setRetailerId(1)
                .setRetailerStoreId("12")
                .setScheduleType("dispatch")
                .setSecondsForAssemblyItem(Long.parseLong("300"))
                .setStoreZones("[{\"id\":70,\"name\":\"Зона Дорожная\",\"area\":[[[37.6346352,55.684103000000015],[37.628947,55.70151000000001],[37.629633,55.70518400000001],[37.623694,55.70585499999999],[37.621737,55.705837],[37.619092,55.705325999999985],[37.6186622,55.705132000000006],[37.6081052,55.701553],[37.5888792,55.70682500000001],[37.5831292,55.70970210000001],[37.5735422,55.687786],[37.5082692,55.62179200000001],[37.4911022,55.6108373],[37.5087839,55.596194999999994],[37.5686932,55.58125600000001],[37.5535872,55.56960999999999],[37.5533372,55.557857000000006],[37.5831122,55.55038800000001],[37.5999352,55.55932099999999],[37.6030252,55.56534],[37.5966742,55.576113],[37.6706602,55.572328],[37.6880842,55.575637],[37.68037819999999,55.58318299999999],[37.668919,55.60412000000001],[37.662927,55.60253199999999],[37.6578712,55.602014],[37.6494072,55.602391999999995],[37.64135420000001,55.610046],[37.6359602,55.613774000000014],[37.6329252,55.61867899999999],[37.6286602,55.62777100000002],[37.6257712,55.63855699999999],[37.657571,55.651055],[37.6705744,55.65463840000001],[37.672806,55.66441899999999],[37.676239,55.66829100000001],[37.685913,55.670838],[37.695009,55.67196579999999],[37.7030751,55.67096320000001],[37.709593000000005,55.666827700000006],[37.7142177,55.6683341],[37.71454210000001,55.67231490000002],[37.7109019,55.68017930000001],[37.7125856,55.68495279999999],[37.70634,55.688694],[37.695611,55.69145100000001],[37.6852854,55.690934199999994],[37.6657497,55.68637699999999],[37.6453916,55.6846066],[37.6346352,55.684103000000015]]]}]")
                .setTimeZone("Europe/Moscow")
                .setUpdatedAt(getTimestamp())
                .setUuid(kafkaStoreUUIDKey)
                .build();
        kafka.publish(configStoreChanged(), kafkaStoreUUIDKey, storeChanged);
    }

    @AfterClass(alwaysRun = true, description = "Clear")
    public void after() {
        boolean delete = PlanningAreasDao.INSTANCE.delete(baseStoreId);
        if (delete) {
            ShopsDao.INSTANCE.delete(baseStoreId);
        }
    }

    @TmsLink("177")
    @Story("Создание зон планирования из стрима магазинов")
    @Test(groups = {"api-shifts"},
            description = "Создание зоны планирования для магазина с быстрой доставкой и территорией доставки")
    public void createStore() {
        var store = ShopsDao.INSTANCE.getOriginalId(baseStoreId);
        Allure.step("Asserts", () ->
                assertEquals(store.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }

    @TmsLink("178")
    @Story("Создание зон планирования из стрима магазинов")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "createStore",
            description = "Создание зоны планирования для магазина с быстрой доставкой и территорией доставки")
    public void createStore2() {
        var storeChanged = StoreChangedOuterClass.StoreChanged.newBuilder()
                .setAdditionalSecondsForAssembly(600)
                .setAutoRouting(true)
                .setAvailableOn(getTimestamp())
                .setBaseStoreId(baseStoreId)
                .setClosingTime("23:00")
                .setCreatedAt(getTimestamp())
                .setDeliveryAreaId(deliveryAreaId)
                .setDispatchSettings(DispatchSettings.newBuilder()
                        .setAdditionalFactorForStraightDistanceToClientMin(5L)
                        .setAverageSpeedForStraightDistanceToClientMin(3L)
                        .setAvgParkingMinVehicle(5L)
                        .setAvgToPlaceMin(15L)
                        .setAvgToPlaceMinExternal(20L)
                        .setGapTaxiPunishMin(15L)
                        .setLastPositionExpire(180L)
                        .setMaxCurrentOrderAssignQueue(5L)
                        .setMaxOrderAssignRetryCount(5L)
                        .setMaxWaitingTimeForCourierMin(5L)
                        .setOfferSeenTimeoutSec(60L)
                        .setOfferServerTimeoutSec(60L)
                        .setOrderReceiveTimeFromAssemblyToDeliveryMin(5L)
                        .setOrderTransferTimeFromAssemblyToDeliveryMin(5L)
                        .setOrderTransferTimeFromDeliveryToClientMin(3L)
                        .setOrderWeightThresholdToAssignToVehicleGramms(10000L)
                        .build())
                .setExpressDelivery(true)
                .setHelpdeskeddyId(1234L)
                .setId(baseStoreId)
                .setImportKeyPostfix("999")
                .setLocation("{\"id\":95,\"lat\":55.630794,\"lon\":37.624798,\"area\":null,\"city\":\"Москва\",\"kind\":null,\"block\":\"\",\"floor\":null,\"phone\":null,\"region\":null,\"street\":\"Дорожная\",\"building\":\" д. 1, корп. 1\",\"comments\":null,\"entrance\":null,\"apartment\":null,\"door_phone\":null,\"settlement\":null,\"elevator\":null,\"delivery_to_door\":false,\"full_address\":\"Москва, Дорожная,  д. 1, корп. 1\"}")
                .setName("METRO, Тестовая_EDIT_" + baseStoreId)
                .setOpeningTime("01:00")
                .setOperationalZoneId(1)
                .setOperationalZoneName("Москва")
                .setPlaceSettings(StoreChangedOuterClass.PlaceSettings.newBuilder()
                        .setAssemblyTaskType(AssemblyType.SM)
                        .setDeliveryTaskType(DeliveryType.SM)
                        .addPlaceAvailableTasksToBeAssigned(AvailableTasks.ASSEMBLY)
                        .addPlaceAvailableTasksToBeAssigned(AvailableTasks.DELIVERY)
                        .setPlaceLocation(StoreChangedOuterClass.LocationPoint.newBuilder()
                                .setLat(55.630794)
                                .setLon(37.624798)
                                .build())
                        .build())
                .setRetailerId(1)
                .setRetailerStoreId("12")
                .setScheduleType("dispatch")
                .setSecondsForAssemblyItem(Long.parseLong("300"))
                .setStoreZones("[{\"id\":70,\"name\":\"Зона Дорожная\",\"area\":[[[37.6346352,55.684103000000015],[37.628947,55.70151000000001],[37.629633,55.70518400000001],[37.623694,55.70585499999999],[37.621737,55.705837],[37.619092,55.705325999999985],[37.6186622,55.705132000000006],[37.6081052,55.701553],[37.5888792,55.70682500000001],[37.5831292,55.70970210000001],[37.5735422,55.687786],[37.5082692,55.62179200000001],[37.4911022,55.6108373],[37.5087839,55.596194999999994],[37.5686932,55.58125600000001],[37.5535872,55.56960999999999],[37.5533372,55.557857000000006],[37.5831122,55.55038800000001],[37.5999352,55.55932099999999],[37.6030252,55.56534],[37.5966742,55.576113],[37.6706602,55.572328],[37.6880842,55.575637],[37.68037819999999,55.58318299999999],[37.668919,55.60412000000001],[37.662927,55.60253199999999],[37.6578712,55.602014],[37.6494072,55.602391999999995],[37.64135420000001,55.610046],[37.6359602,55.613774000000014],[37.6329252,55.61867899999999],[37.6286602,55.62777100000002],[37.6257712,55.63855699999999],[37.657571,55.651055],[37.6705744,55.65463840000001],[37.672806,55.66441899999999],[37.676239,55.66829100000001],[37.685913,55.670838],[37.695009,55.67196579999999],[37.7030751,55.67096320000001],[37.709593000000005,55.666827700000006],[37.7142177,55.6683341],[37.71454210000001,55.67231490000002],[37.7109019,55.68017930000001],[37.7125856,55.68495279999999],[37.70634,55.688694],[37.695611,55.69145100000001],[37.6852854,55.690934199999994],[37.6657497,55.68637699999999],[37.6453916,55.6846066],[37.6346352,55.684103000000015]]]}]")
                .setTimeZone("Europe/Moscow")
                .setUpdatedAt(getTimestamp())
                .setUuid(kafkaStoreUUIDKey)
                .build();

        kafka.publish(configStoreChanged(), storeChanged);

        var store = ShopsDao.INSTANCE.getOriginalId(baseStoreId);
        Allure.step("Asserts", () ->
                assertEquals(store.size(), 1, "Данные shops пустые или вернулось более одного магазина")
        );
    }


    @TmsLink("81")
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

    @TmsLink("81")
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

        Allure.step("", () -> {
            assertNotEquals(planningPeriodsBefore.get(0).getGuaranteedPayroll(), planningPeriodsAfter.get(0).getGuaranteedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(1).getGuaranteedPayroll(), planningPeriodsAfter.get(1).getGuaranteedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(0).getPredictedPayroll(), planningPeriodsAfter.get(0).getPredictedPayroll(), "Данные совпадают");
            assertNotEquals(planningPeriodsBefore.get(1).getPredictedPayroll(), planningPeriodsAfter.get(1).getPredictedPayroll(), "Данные совпадают");
        });
    }

    @TmsLink("136")
    @Story("Импорт плановых периодов")
    @Skip
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

    @TmsLink("204")
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
