package ru.instamart.api.helper;

import com.google.protobuf.Timestamp;
import events.CandidateChangesOuterClass;
import io.qameta.allure.Step;
import norns.Norns;
import order.OrderChanged;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.ConfigInheritanceDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.*;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

public class SurgeLevelHelper {

    private static final KafkaHelper kafka = new KafkaHelper();

    @Step("Создаем событие отправки заказа в kafka для магазина {storeId}")
    public static OrderChanged.EventOrderChanged getEventOrderStatus(String orderUuid, String storeId, OrderChanged.EventOrderChanged.OrderStatus orderStatus, OrderChanged.EventOrderChanged.ShipmentType shipmentType, String shipmentUuid) {
        return OrderChanged.EventOrderChanged.newBuilder()
                .setOrderUuid(orderUuid)
                .setPlaceUuid(storeId)
                .setOrderStatus(orderStatus)
                .setShipmentType(shipmentType)
                .setShipmentUuid(shipmentUuid)
                .build();
    }

    @Step("Отправляем событие отправки заказа в kafka для магазина {storeId}")
    public static void publishEventOrderStatus(String orderUuid, String storeId, OrderChanged.EventOrderChanged.OrderStatus orderStatus, OrderChanged.EventOrderChanged.ShipmentType shipmentType, String shipmentUuid, int timeout) {
        OrderChanged.EventOrderChanged eventOrder = getEventOrderStatus(orderUuid, storeId, orderStatus, shipmentType, shipmentUuid);
        kafka.publish(configOrderStatusChanged(), eventOrder);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Проверяем отправку расчета surgelevel в kafka для магазина {storeId}")
    public static void checkSurgeLevelProduce(List<Surgelevelevent.SurgeEvent> surgeLevels, int surgeEventsAmount, String storeId, float pastSurgeLevel, float currentSurgeLevel, int currentDemandAmount, int currentSupplyAmount) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getStoreId(), storeId, "Не верный uuid магазина");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPastSurgeLevel(), pastSurgeLevel, "Не верный прошлый surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPresentSurgeLevel(), currentSurgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getShipmentQuantity(), currentDemandAmount, "Не верное кол-во заказов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getCandidateQuantity(), currentSupplyAmount, "Не верное кол-во кандидатов");
        softAssert.assertAll();
    }

    @Step("Добавить магазин {storeId} и конфиг для него в БД")
    public static void addStore(String storeId, String configId, boolean isOnDemand, float storeLat, float storeLon, String formulaId, int step, int deliveryArea) {
        boolean isConfigCreated = ConfigDao.INSTANCE.addConfig(configId, formulaId, step);
        assertTrue(isConfigCreated, "Не добавился конфиг");
        boolean isStoreCreated = StoreDao.INSTANCE.addStore(storeId, configId, isOnDemand, storeLat, storeLon, deliveryArea);
        assertTrue(isStoreCreated, "Не добавился магазин");
    }

    @Step("Удалить магазин {storeId} и конфиг для него из БД")
    public static void deleteStore(String storeId) {
        if (Objects.nonNull(storeId)) {
            StoreDao.INSTANCE.delete(storeId);
            ConfigDao.INSTANCE.deleteByStore(storeId);
        }
    }

    @Step("Создаем событие отправки статуса кандидата {candidateUuid}")
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

    @Step("Отправляем событие отправки статуса кандидата {candidateUuid}")
    public static void publishEventCandidateStatus(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int timeout) {
        CandidateChangesOuterClass.CandidateChanges eventCandidate = getEventCandidateStatus(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие отправки статуса кандидата {candidateUuid} с workflow")
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

    @Step("Отправляем событие отправки статуса кандидата {candidateUuid} c workflow")
    public static void publishEventCandidateStatusWithWorkflow(String candidateUuid, CandidateChangesOuterClass.CandidateChanges.Role candidateRole, CandidateChangesOuterClass.CandidateChanges.Status candidateStatus, int shiftId, int deliveryAreaId, boolean fixedOnDeliveryAreaOrStore, String storeId, String storeScheduleType, int workflowId, CandidateChangesOuterClass.WorkflowStatus workflowStatus, Timestamp workflowPlanEnded, float workflowLat, float workflowLon, int timeout) {
        CandidateChangesOuterClass.CandidateChanges eventCandidate = getEventCandidateStatusWithWorkflow(candidateUuid, candidateRole, candidateStatus, shiftId, deliveryAreaId, fixedOnDeliveryAreaOrStore, storeId, storeScheduleType, workflowId, workflowStatus, workflowPlanEnded, workflowLat, workflowLon);
        kafka.publish(configCandidateStatus(), eventCandidate);
        ThreadUtil.simplyAwait(timeout);
    }

    @Step("Создаем событие отправки геолокации кандидата {candidateUuid}")
    public static Norns.EventAddLocation getEventLocation(String userUuid, float lat, float lon, boolean isFakeGpsAppDetected) {
        return Norns.EventAddLocation.newBuilder()
                .setUserUuid(userUuid)
                .setLat(lat)
                .setLon(lon)
                .setTime(getTimestamp())
                .setIsFakeGpsAppDetected(isFakeGpsAppDetected)
                .build();
    }

    @Step("Отправляем событие отправки геолокации кандидата {candidateUuid}")
    public static void publishEventLocation(String userUuid, float lat, float lon, boolean isFakeGpsAppDetected, int timeout) {
        Norns.EventAddLocation eventLocation = getEventLocation(userUuid, lat, lon, isFakeGpsAppDetected);
        kafka.publish(configNorns(), eventLocation);
        ThreadUtil.simplyAwait(timeout);
    }
}
