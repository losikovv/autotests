package ru.instamart.api.helper;

import com.google.protobuf.Timestamp;
import events.CandidateChangesOuterClass;
import io.qameta.allure.Step;
import norns.Norns;
import order.OrderChanged;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.FormulaDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.jdbc.entity.surgelevel.FormulaEntity;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.*;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

public class SurgeLevelHelper {

    private static final KafkaHelper kafka = new KafkaHelper();

    @Step("Создаем событие заказа для магазина {storeId}")
    public static OrderChanged.EventOrderChanged getEventOrderStatus(String orderUuid, String storeId, OrderChanged.EventOrderChanged.OrderStatus orderStatus, OrderChanged.EventOrderChanged.ShipmentType shipmentType, String shipmentUuid) {
        return OrderChanged.EventOrderChanged.newBuilder()
                .setOrderUuid(orderUuid)
                .setPlaceUuid(storeId)
                .setOrderStatus(orderStatus)
                .setShipmentType(shipmentType)
                .setShipmentUuid(shipmentUuid)
                .build();
    }

    @Step("Отправляем событие заказа для магазина {storeId} в kafka")
    public static void publishEventOrderStatus(String orderUuid, String storeId, OrderChanged.EventOrderChanged.OrderStatus orderStatus, OrderChanged.EventOrderChanged.ShipmentType shipmentType, String shipmentUuid, int timeout) {
        OrderChanged.EventOrderChanged eventOrder = getEventOrderStatus(orderUuid, storeId, orderStatus, shipmentType, shipmentUuid);
        kafka.publish(configOrderStatusChanged(), eventOrder);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Проверяем отправку расчета surgelevel в kafka для магазина {storeId}")
    public static void checkSurgeLevelProduce(List<Surgelevelevent.SurgeEvent> surgeLevels, int surgeEventsAmount, String storeId, float pastSurgeLevel, float currentSurgeLevel, int currentDemandAmount, int currentSupplyAmount,  Surgelevelevent.SurgeEvent.Method method) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getStoreId(), storeId, "Не верный uuid магазина");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPastSurgeLevel(), pastSurgeLevel, "Не верный прошлый surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPresentSurgeLevel(), currentSurgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getShipmentQuantity(), currentDemandAmount, "Не верное кол-во заказов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getCandidateQuantity(), currentSupplyAmount, "Не верное кол-во кандидатов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getMethod(), method, "Не верный метод расчета");
        softAssert.assertAll();
    }

    @Step("Добавить магазин {storeId} и конфиг для него в БД")
    public static void addStore(String storeId, String configId, Boolean isDisabled, boolean isOnDemand, float storeLat, float storeLon, String formulaId, int step, int deliveryArea) {
        boolean isConfigCreated = ConfigDao.INSTANCE.addConfig(configId, formulaId, step, isDisabled);
        assertTrue(isConfigCreated, "Не добавился конфиг");
        boolean isStoreCreated = StoreDao.INSTANCE.addStore(storeId, configId, isOnDemand, storeLat, storeLon, deliveryArea);
        assertTrue(isStoreCreated, "Не добавился магазин");
    }

    @Step("Создаем событие статуса кандидата {candidateUuid}")
    public static CandidateChangesOuterClass.CandidateChanges getEventCandidateStatus(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType) {
        return CandidateChangesOuterClass.CandidateChanges.newBuilder()
                .setUuid(candidateUuid)
                .setRole(candidateRole)
                .setStatus(candidateStatus)
                .setTime(getTimestamp())
                .setShift(CandidateChangesOuterClass.CandidateShift.newBuilder()
                        .setShiftId(shiftId)
                        .setDeliveryAreaId(deliveryAreaId)
                        .setFixedOnDeliveryAreaOrStore(fixedOnDeliveryAreaOrStore)
                        .setStoreUuid(storeId)
                        .build())
                .setStoreScheduleType(storeScheduleType)
                .build();
    }

    @Step("Отправляем событие статуса кандидата {candidateUuid} в kafka")
    public static void publishEventCandidateStatus(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int timeout) {
        CandidateChangesOuterClass.CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие статуса кандидата {candidateUuid} с workflow")
    public static CandidateChangesOuterClass.CandidateChanges getEventCandidateStatusWithWorkflow(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int workflowId, CandidateChangesOuterClass.WorkflowStatus workflowStatus, Timestamp workflowPlanEnded, float workflowLat, float workflowLon) {
        return CandidateChangesOuterClass.CandidateChanges.newBuilder()
                .setUuid(candidateUuid)
                .setRole(candidateRole)
                .setStatus(candidateStatus)
                .setTime(getTimestamp())
                .setShift(CandidateChangesOuterClass.CandidateShift.newBuilder()
                        .setShiftId(shiftId)
                        .setDeliveryAreaId(deliveryAreaId)
                        .setFixedOnDeliveryAreaOrStore(fixedOnDeliveryAreaOrStore)
                        .setStoreUuid(storeId)
                        .build())
                .setStoreScheduleType(storeScheduleType)
                .addQueuedWorkflows(CandidateChangesOuterClass.Workflow.newBuilder()
                        .setId(workflowId)
                        .setStatus(workflowStatus)
                        .setShiftId(shiftId)
                        .setCreatedAt(getTimestamp())
                        .setUpdatedAt(getTimestamp())
                        .setPlanEndedAt(workflowPlanEnded)
                        .setCoordinatesEnd(CandidateChangesOuterClass.Location.newBuilder()
                                .setLat(workflowLat)
                                .setLon(workflowLon)
                                .build())
                        .build())
                .build();
    }

    @Step("Отправляем событие статуса кандидата {candidateUuid} c workflow в kafka")
    public static void publishEventCandidateStatusWithWorkflow(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int workflowId, CandidateChangesOuterClass.WorkflowStatus workflowStatus, Timestamp workflowPlanEnded, float workflowLat, float workflowLon, int timeout) {
        CandidateChangesOuterClass.CandidateChanges eventCandidate = getEventCandidateStatusWithWorkflow(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType, workflowId, workflowStatus, workflowPlanEnded, workflowLat, workflowLon);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие геолокации кандидата {candidateUuid}")
    public static Norns.EventAddLocation getEventLocation(String userUuid, float lat, float lon, boolean isFakeGpsAppDetected) {
        return Norns.EventAddLocation.newBuilder()
                .setUserUuid(userUuid)
                .setLat(lat)
                .setLon(lon)
                .setTime(getTimestamp())
                .setIsFakeGpsAppDetected(isFakeGpsAppDetected)
                .build();
    }

    @Step("Отправляем событие геолокации кандидата {candidateUuid} в kafka")
    public static void publishEventLocation(String userUuid, float lat, float lon, boolean isFakeGpsAppDetected, int timeout) {
        Norns.EventAddLocation eventLocation = getEventLocation(userUuid, lat, lon, isFakeGpsAppDetected);
        kafka.publish(configNorns(), eventLocation);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Проверить наличие кастомной формулы расчета surgelevel в БД")
    public static void checkFormula(String formulaId) {
        FormulaEntity formula = FormulaDao.INSTANCE.findFormula(formulaId);
        if (!Objects.nonNull(formula)){
            String script = "function main(arg) {\n" +
                    "    var demand = 150\n" +
                    "    var supply = 50\n" +
                    "\n" +
                    "    for (var i = 0; i < arg.supply.length; i++) { \n" +
                    "        if (!arg.supply[i].candidate.busy) {\n" +
                    "            supply++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    for (var i = 0; i < arg.demand.length; i++) { \n" +
                    "        if (arg.demand[i].shipment.status>= 0 && arg.demand[i].shipment.status<=3) {\n" +
                    "            demand++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    return demand - supply;\n" +
                    "}";
            boolean isFormulaCreated = FormulaDao.INSTANCE.addFormula(formulaId, "Kraken-tests formula", script);
            assertTrue(isFormulaCreated, "Не добавилась формула");
        }
    }
}
