package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ShipmentsRequest {

    /**
     * Получаем заказ
     */
    @Step("{method} /" + ShopperApiEndpoints.Shipments.ID)
    public static Response GET(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shipments.ID, shipmentId);
    }

    public static class Stocks {
        /**
         * Получаем стоки заказа
         */
        @Step("{method} /" + ShopperApiEndpoints.Shipments.STOCKS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shipments.STOCKS, shipmentId);
        }
    }
    public static class MarketingSampleItems {
        @Step("{method} /" + ShopperApiEndpoints.Shipments.MARKETING_SAMPLE_ITEMS)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shipments.MARKETING_SAMPLE_ITEMS, shipmentId);
        }
    }
}
