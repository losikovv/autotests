package ru.instamart.api.helper;

import events.CandidateChangesOuterClass;
import io.qameta.allure.Step;
import norns.Norns;
import order.OrderChanged;
import order_enrichment.OrderEnrichment;
import order_status.OrderStatus;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.ConfigInheritanceDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

public class SurgeLevelHelper {

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
    public static void addStore(String storeId, String configId, boolean isOnDemand, float storeLat, float storeLon, String inheritedConfigId, int priority, int deliveryArea) {
        boolean isConfigCreated = ConfigDao.INSTANCE.addConfig(configId);
        assertTrue(isConfigCreated, "Не добавился конфиг");
        boolean isStoreCreated = StoreDao.INSTANCE.addStore(storeId, configId, isOnDemand, storeLat, storeLon, deliveryArea);
        assertTrue(isStoreCreated, "Не добавился магазин");
        boolean isInheritanceCreated = ConfigInheritanceDao.INSTANCE.addConfigInheritance(configId, inheritedConfigId, priority);
        assertTrue(isInheritanceCreated, "Не добавилась зависимости");
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
}
