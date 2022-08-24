package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ProductFeedbacksV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PRODUCT_FEEDBACKS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.PRODUCT_FEEDBACKS);
    }

    @Step("{method} /" + ApiV2EndPoints.PRODUCT_FEEDBACKS)
    public static Response GET(final String storeId, final String sku) {
        return givenWithAuth()
                .queryParam("store_id", storeId)
                .queryParam("sku", sku)
                .get(ApiV2EndPoints.PRODUCT_FEEDBACKS);
    }

    @Step("{method} /" + ApiV2EndPoints.PRODUCT_FEEDBACKS)
    public static Response POST(final Feedbacks feedbacks) {
        return givenWithAuth()
                .body(feedbacks)
                .post(ApiV2EndPoints.PRODUCT_FEEDBACKS);
    }

    @Step("{method} /" + ApiV2EndPoints.ProductFeedbacks.BY_ID)
    public static Response DELETE(final String id) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.ProductFeedbacks.BY_ID, id);
    }

    public static class CanPostFeedback {

        @Step("{method} /" + ApiV2EndPoints.ProductFeedbacks.CAN_POST_FEEDBACK)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.ProductFeedbacks.CAN_POST_FEEDBACK);
        }

        @Step("{method} /" + ApiV2EndPoints.ProductFeedbacks.CAN_POST_FEEDBACK)
        public static Response GET(final String storeId, final String sku) {
            return givenWithAuth()
                    .queryParam("store_id", storeId)
                    .queryParam("sku", sku)
                    .get(ApiV2EndPoints.ProductFeedbacks.CAN_POST_FEEDBACK);
        }
    }

    public static class ActualFeedback {

        @Step("{method} /" + ApiV2EndPoints.ProductFeedbacks.ACTUAL_FEEDBACK)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.ProductFeedbacks.ACTUAL_FEEDBACK);
        }

        @Step("{method} /" + ApiV2EndPoints.ProductFeedbacks.ACTUAL_FEEDBACK)
        public static Response GET(final String storeId, final String sku) {
            return givenWithAuth()
                    .queryParam("store_id", storeId)
                    .queryParam("sku", sku)
                    .get(ApiV2EndPoints.ProductFeedbacks.ACTUAL_FEEDBACK);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class Feedbacks{
            private String sku;
            @JsonProperty("store_id")
            private Integer storeId;
            private Integer score;
            private String pros;
            private String cons;
            private String text;
    }
}
