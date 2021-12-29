package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.*;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.sbermarket.common.Mapper;

import static ru.instamart.kraken.util.TimeUtil.*;

public class StoresV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.STORES)
    public static Response GET() {
        return givenWithSpec().get(ApiV1Endpoints.STORES);
    }

    @Step("{method} /" + ApiV1Endpoints.Stores.UUID)
    public static Response GET(String storeUuid) {
        return givenWithSpec().get(ApiV1Endpoints.Stores.UUID, storeUuid);
    }

    public static class Offers {
        @Step("{method} /" + ApiV1Endpoints.Stores.OFFERS)
        public static Response GET(String storeUuid, String offerName, String offerRetailerSku) {
            return givenWithSpec().get(ApiV1Endpoints.Stores.OFFERS, storeUuid, offerName, offerRetailerSku);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.Offers.SEARCH)
        public static Response GET(Integer storeId, String skus) {
            RequestSpecification req = givenWithSpec();
            if (skus != null) req.queryParams("skus", skus);
            return req.get(ApiV1Endpoints.Stores.StoreId.Offers.SEARCH, storeId);
        }
    }

    public static class DeliveryWindows {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.DeliveryWindows.BY_DATE)
        public static Response GET(Integer storeId) {
            String date = getFutureDateWithoutTime(1L);
            return givenWithAuth()
                    .get(ApiV1Endpoints.Stores.StoreId.DeliveryWindows.BY_DATE, storeId, date);
        }


        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.DeliveryWindows.GENERATE)
        public static Response POST(Integer storeId) {
            JSONObject body = new JSONObject();
            body.put("starting_date", getZonedDate());
            body.put("ending_date", getZonedFutureDate(6L));
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Stores.StoreId.DeliveryWindows.GENERATE, storeId);
        }
    }

    public static class Products {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.Products.BY_PERMALINK)
        public static Response GET(Integer storeId, String permalink) {
            return givenWithSpec()
                    .get(ApiV1Endpoints.Stores.StoreId.Products.BY_PERMALINK, storeId, permalink);
        }
    }

    public static class NextDeliveries {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.NEXT_DELIVERIES)
        public static Response GET(Integer storeId, NextDeliveriesParams params){
            return givenWithAuth()
                    .queryParams(Mapper.INSTANCE.objectToMap(params))
                    .get(ApiV1Endpoints.Stores.StoreId.NEXT_DELIVERIES, storeId);
        }
    }

    public static class SearchSuggestions {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.SEARCH_SUGGESTIONS)
        public static Response GET(Integer storeId, String query){
            return givenWithSpec()
                    .queryParam("q", query)
                    .get(ApiV1Endpoints.Stores.StoreId.SEARCH_SUGGESTIONS, storeId);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NextDeliveriesParams {
        private Boolean cargo;
        @JsonProperty(value = "shipping_method")
        private String shippingMethod;
        private Double lat;
        private Double lon;
    }
}
