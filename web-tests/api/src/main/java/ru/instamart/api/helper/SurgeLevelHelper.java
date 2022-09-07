package ru.instamart.api.helper;

import io.qameta.allure.Step;
import order_enrichment.OrderEnrichment;
import org.testng.asserts.SoftAssert;
import surgelevelevent.Surgelevelevent;

import java.util.List;

public class SurgeLevelHelper {

    @Step("Получаем событие отправки заказа EventOrderEnrichment в kafka ")
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

    @Step("Проверка отправки расчета surgelevel в kafka")
    public static void checkSurgeLevelProduce(List<Surgelevelevent.SurgeEvent> surgeLevels, int surgeEventsAmount, String storeId, float pastSurgeLevel, float currentSurgeLevel, int currentDemandAmount, int currentSupplyAmount) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getStoreId(), storeId, "Не верный uuid магазина");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPastSurgeLevel(), pastSurgeLevel, "Не верный прошлый surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getPresentSurgeLevel(), currentSurgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getShipmentQuantity(), currentDemandAmount, "Не верное кол-во заказов");
        softAssert.assertEquals(surgeLevels.get(surgeEventsAmount - 1).getInfo().getCandidateQuantity(), currentSupplyAmount, "Не верное кол-во кандидатов");
        softAssert.assertAll();
    }
}
