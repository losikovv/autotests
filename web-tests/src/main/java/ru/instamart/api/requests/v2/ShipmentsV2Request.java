package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class ShipmentsV2Request {

    public static class ShippingRates {
        /**
         * Получаем доступные слоты
         */
        @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPPING_RATES)
        public static Response GET(String shipmentNumber) {
            return givenWithAuthApiV2()
                    .get(ApiV2EndPoints.Shipments.SHIPPING_RATES, shipmentNumber);
        }
    }
}
