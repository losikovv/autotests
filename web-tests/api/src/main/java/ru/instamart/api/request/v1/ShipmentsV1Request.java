package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class ShipmentsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Shipments.NUMBER)
    public static Response GET(String shipmentNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Shipments.NUMBER, shipmentNumber);
    }

    public static class Products {
        public static class Prereplacements {
            @Step("{method} /" + ApiV1Endpoints.Shipments.Products.PREREPLACEMENTS)
            public static Response GET(String shipmentNumber, long productSku) {
                return givenWithAuth().get(ApiV1Endpoints.Shipments.Products.PREREPLACEMENTS, shipmentNumber, productSku);
            }
        }
    }

    public static class Offers {
        @Step("{method} /" + ApiV1Endpoints.Shipments.OFFERS)
        public static Response GET(String shipmentNumber) {
            return givenWithSpec().get(ApiV1Endpoints.Shipments.OFFERS, shipmentNumber);
        }
    }

    public static class ShippingRates {
        @Step("{method} /" + ApiV1Endpoints.Shipments.SHIPPING_RATES)
        public static Response GET(String shipmentNumber, String date) {
            if (Objects.nonNull(date))
                date = "date=" + date;
            else
                date = "";
            return givenWithAuth()
                    .get(ApiV1Endpoints.Shipments.SHIPPING_RATES, shipmentNumber, date);
        }
    }
}
