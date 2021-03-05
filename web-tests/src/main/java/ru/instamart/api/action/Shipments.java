package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuth;

public final class Shipments {

    public static class ShippingRates {
        /**
         * Получаем доступные слоты
         */
        @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPPING_RATES)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.SHIPPING_RATES, shipmentNumber);
        }
    }
}
