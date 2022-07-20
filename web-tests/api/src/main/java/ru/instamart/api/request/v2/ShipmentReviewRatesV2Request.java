package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.sbermarket.common.Mapper;

import java.util.List;

public class ShipmentReviewRatesV2Request extends ApiV2RequestBase {

//    @Step("{method} /" + ApiV2EndPoints.SeparateReviews.SHIPMENT_REVIEW_RATES)
//    public static Response POST(String shipmentNumber, int shippingRate, int assemblyRate) {
//        JSONObject body = new JSONObject();
//        JSONObject assembly = new JSONObject();
//        JSONArray issueIds = new JSONArray();
//        JSONObject shipping = new JSONObject();
//        body.put("shipment_number", shipmentNumber);
//
//        assembly.put("rate", assemblyRate);
//        assembly.put("issue_ids", issueIds);
//        body.put("assembly", assembly);
//
//        shipping.put("rate", shippingRate);
//        shipping.put("issue_ids", issueIds);
//        body.put("shipping", shipping);
//
//        return givenWithAuth()
//                .header("client-ver", "6.21")
//                .contentType(ContentType.JSON)
//                .body(body)
//                .post(ApiV2EndPoints.SeparateReviews.SHIPMENT_REVIEW_RATES);
//    }

    @Step("{method} /" + ApiV2EndPoints.SeparateReviews.SHIPMENT_REVIEW_RATES)
    public static Response POST(String shipmentNumber, int shippingRate, int assemblyRate) {
        var body = ShippingRates.builder()
                .shipmentNumber(shipmentNumber)
                .shipping(ShippingRates.Rate
                        .builder()
                        .rate(shippingRate)
                        .build())
                .assembly(ShippingRates.Rate
                        .builder()
                        .rate(assemblyRate)
                        .build())
                .build();

        return givenWithAuth()
                .header("client-ver", "6.21")
                .contentType(ContentType.JSON)
                .body(Mapper.INSTANCE.objectToMap(body))
                .post(ApiV2EndPoints.SeparateReviews.SHIPMENT_REVIEW_RATES);
    }

    @Builder
    @Data
    public static final class ShippingRates {
        @JsonProperty("shipment_number")
        private String shipmentNumber;
        private Rate shipping;
        private Rate assembly;

        @Builder
        @Data
        public static final class Rate {
            private int rate;
            @JsonProperty("issue_ids")
            List<String> issueIds;
        }
    }
}
