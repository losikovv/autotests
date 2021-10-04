package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.enums.shopper.AssemblyStateSHP;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.utils.Mapper;

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
                return givenWithSpec().get(ApiV1Endpoints.Shipments.Products.PREREPLACEMENTS, shipmentNumber, productSku);
            }
        }
    }

    public static class Offers {
        @Step("{method} /" + ApiV1Endpoints.Shipments.OFFERS)
        public static Response GET(String shipmentNumber) {
            return givenWithSpec().get(ApiV1Endpoints.Shipments.OFFERS, shipmentNumber);
        }
    }
}
