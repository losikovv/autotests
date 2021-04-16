package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class ShipmentsV2Request extends ApiV2RequestBase {

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
