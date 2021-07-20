package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.Objects;

public final class ShipmentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPMENTS)
    public static Response DELETE(String shipmentNumber) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.Shipments.SHIPMENTS, shipmentNumber);
    }

    public static class DeliveryWindows {

        @Step("{method} /" + ApiV2EndPoints.Shipments.DELIVERY_WINDOWS)
        public static Response GET(String shipmentId, String date) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.DELIVERY_WINDOWS, shipmentId, date);
        }
    }

    public static class LineItems {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEMS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEMS, shipmentNumber);
        }
    }

    public static class LineItemCancellations {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS, shipmentNumber);
        }
    }

    public static class LineItemReplacements {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS)
        public static Response GET(String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS, shipmentNumber);
        }
    }

    public static class ServiceRate {

        public static Response GET(String shipmentNumber, String deliveryWindowId) {
            if (Objects.nonNull(deliveryWindowId))
                deliveryWindowId = "delivery_window_id=" + deliveryWindowId;
            else
                deliveryWindowId = "";
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.SERVICE_RATE, shipmentNumber, deliveryWindowId);
        }
    }

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
