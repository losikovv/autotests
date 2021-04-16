package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

public final class ShipmentsSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем заказ
     */
    @Step("{method} /" + ShopperAppEndpoints.Shipments.ID)
    public static Response GET(String shipmentId) {
        return givenWithAuth()
                .get(ShopperAppEndpoints.Shipments.ID, shipmentId);
    }

    public static class Stocks {
        /**
         * Получаем стоки заказа
         */
        @Step("{method} /" + ShopperAppEndpoints.Shipments.STOCKS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Shipments.STOCKS, shipmentId);
        }
    }
    public static class MarketingSampleItems {
        @Step("{method} /" + ShopperAppEndpoints.Shipments.MARKETING_SAMPLE_ITEMS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Shipments.MARKETING_SAMPLE_ITEMS, shipmentId);
        }
    }
}
