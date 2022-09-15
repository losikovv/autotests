package ru.instamart.api.helper;

import io.qameta.allure.Step;
import order_enrichment.OrderEnrichment;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.surgelevel.ConfigDao;
import ru.instamart.jdbc.dao.surgelevel.ConfigInheritanceDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertTrue;

public class SurgeLevelHelper {

    @Step("Получаем событие отправки заказа EventOrderEnrichment в kafka для магазина: {storeId}")
    public static OrderEnrichment.EventOrderEnrichment getEventOrderEnrichment(int deliveryAreaId, String orderUuid, String storeId, OrderEnrichment.EventOrderEnrichment.ShipmentStatus shipmentStatus, String shipmentType, String shipmentUuid) {
        return OrderEnrichment.EventOrderEnrichment.newBuilder()
                .setDeliveryAreaId(deliveryAreaId)
                .setOrderUuid(orderUuid)
                .setPlaceUuid(storeId)
                .setShipmentStatus(shipmentStatus)
                .setShipmentType(shipmentType)
                .setShipmentUuid(shipmentUuid)
                .build();
    }

    @Step("Проверка отправки расчета surgelevel в kafka для магазина: {storeId}")
    public static void checkSurgeLevelProduce(List<Surgelevelevent.SurgeEvent> surgeLevels, int surgeEventsAmount, String storeId, float pastSurgeLevel, float currentSurgeLevel, int currentDemandAmount, int currentSupplyAmount) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getStoreId(), storeId, "Не верный uuid магазина");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPastSurgeLevel(), pastSurgeLevel, "Не верный прошлый surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPresentSurgeLevel(), currentSurgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getShipmentQuantity(), currentDemandAmount, "Не верное кол-во заказов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getCandidateQuantity(), currentSupplyAmount, "Не верное кол-во кандидатов");
        softAssert.assertAll();
    }

    @Step("Добавить магазин и конфиг для него в БД")
    public static void addStore(String storeId, String configId, boolean isOnDemand, float storeLat, float storeLon, String inheritedConfigId, int priority) {
        boolean isConfigCreated = ConfigDao.INSTANCE.addConfig(configId);
        assertTrue(isConfigCreated, "Не добавился конфиг");
        boolean isStoreCreated = StoreDao.INSTANCE.addStore(storeId, configId, isOnDemand, storeLat, storeLon);
        assertTrue(isStoreCreated, "Не добавился магазин");
        boolean isInheritanceCreated = ConfigInheritanceDao.INSTANCE.addConfigInheritance(configId, inheritedConfigId, priority);
        assertTrue(isInheritanceCreated, "Не добавилась зависимости");
    }

    @Step("Удалить магазин и конфиг для него из БД")
    public static void deleteStore(String storeId) {
        if (Objects.nonNull(storeId)) {
            StoreDao.INSTANCE.delete(storeId);
            ConfigDao.INSTANCE.deleteByStore(storeId);
        }
    }
}
