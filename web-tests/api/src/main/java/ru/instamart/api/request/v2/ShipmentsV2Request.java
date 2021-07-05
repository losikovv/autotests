package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

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

    public static class LineItems {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEMS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEMS, shipmentNumber);
        }
    }

    public static class LineItemCancellations{

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS)
        public static Response GET(String shipmentNumber){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS, shipmentNumber);
        }
    }

    public static class LineItemReplacements{

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS)
        public static Response GET(String shipmentNumber){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS, shipmentNumber);
        }
    }
}
