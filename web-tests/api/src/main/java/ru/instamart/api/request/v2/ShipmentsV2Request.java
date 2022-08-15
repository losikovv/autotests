package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.*;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.sbermarket.common.Mapper;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ShipmentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPMENTS)
    public static Response DELETE(final String shipmentNumber) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.Shipments.SHIPMENTS, shipmentNumber);
    }

    @Step("{method} /" + ApiV2EndPoints.Shipments.ACTIVE)
    public static Response GET(final Integer storeId) {
        RequestSpecification req = givenWithAuth();
        if (Objects.nonNull(storeId)) req.queryParams("store_id", storeId);
        return req.get(ApiV2EndPoints.Shipments.ACTIVE);
    }

    public static class DeliveryWindows {

        @Step("{method} /" + ApiV2EndPoints.Shipments.DELIVERY_WINDOWS)
        public static Response GET(final String shipmentId, final String date) {
            JSONObject query = new JSONObject();
            if (Objects.nonNull(date))
                query.put("date", date);
            return givenWithAuth()
                    .queryParams(Mapper.INSTANCE.objectToMap(query))
                    .get(ApiV2EndPoints.Shipments.DELIVERY_WINDOWS, shipmentId);
        }
    }

    public static class LineItems {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEMS)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEMS, shipmentNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Shipments.LineItems.MERGE)
        public static Response POST(final String shipmentNumber, final Long productId, final Integer quantity) {
            JSONObject body = new JSONObject();
            JSONObject lineItem = new JSONObject();
            lineItem.put("product_id", productId);
            lineItem.put("quantity", quantity);
            lineItem.put("customer_comment", "test");
            body.put("line_item", lineItem);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV2EndPoints.Shipments.LineItems.MERGE, shipmentNumber);
        }
    }

    public static class LineItemCancellations {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_CANCELLATIONS, shipmentNumber);
        }
    }

    public static class LineItemReplacements {

        @Step("{method} /" + ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.LINE_ITEM_REPLACEMENTS, shipmentNumber);
        }
    }

    public static class ServiceRate {

        public static Response GET(final String shipmentNumber, final String deliveryWindowId) {
            JSONObject query = new JSONObject();
            if (Objects.nonNull(deliveryWindowId))
                query.put("delivery_window_id", deliveryWindowId);
            return givenWithAuth()
                    .queryParams(Mapper.INSTANCE.objectToMap(query))
                    .get(ApiV2EndPoints.Shipments.SERVICE_RATE, shipmentNumber);
        }
    }

    public static class ShippingRates {
        /**
         * Получаем доступные слоты
         */
        @Step("{method} /" + ApiV2EndPoints.Shipments.SHIPPING_RATES)
        public static Response GET(@NonNull final String shipmentNumber, final String date) {
            JSONObject query = new JSONObject();
            if (Objects.nonNull(date)) {
                query.put("date", date);
            }
            return givenWithAuth()
                    .queryParams(Mapper.INSTANCE.objectToMap(query))
                    .get(ApiV2EndPoints.Shipments.SHIPPING_RATES, shipmentNumber);
        }
    }

    public static class State {

        @Step("{method} /" + ApiV2EndPoints.Shipments.STATE)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.STATE, shipmentNumber);
        }
    }

    public static class ReviewIssues {

        @Step("{method} /" + ApiV2EndPoints.Shipments.REVIEW_ISSUES)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.REVIEW_ISSUES, shipmentNumber);
        }
    }

    public static class Reviews {

        @Step("{method} /" + ApiV2EndPoints.Shipments.REVIEWS)
        public static Response POST(final String shipmentNumber, final Review review) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(ReviewRequest.builder().review(review).build())
                    .post(ApiV2EndPoints.Shipments.REVIEWS, shipmentNumber);
        }

        @Step("{method} /" + ApiV2EndPoints.Shipments.REVIEWS)
        public static Response POST(final String shipmentNumber) {
            return givenWithAuth()
                    .formParam("review[rate]", 5)
                    .multiPart("review[images_attributes][][attachment]", new File("src/test/resources/data/sample.jpg"), "image/jpeg")
                    .post(ApiV2EndPoints.Shipments.REVIEWS, shipmentNumber);
        }
    }

    public static class ReviewWindowClose {

        @Step("{method} /" + ApiV2EndPoints.Shipments.REVIEW_WINDOW_CLOSE)
        public static Response PUT(final String shipmentNumber) {
            return givenWithAuth()
                    .put(ApiV2EndPoints.Shipments.REVIEW_WINDOW_CLOSE, shipmentNumber);
        }
    }

    public static class Clones {

        @Step("{method} /" + ApiV2EndPoints.Shipments.CLONES)
        public static Response POST(final String shipmentNumber) {
            return givenWithAuth()
                    .post(ApiV2EndPoints.Shipments.CLONES, shipmentNumber);
        }
    }

    public static class AssemblyItems {

        @Step("{method} /" + ApiV2EndPoints.Shipments.ASSEMBLY_ITEMS)
        public static Response GET(final String shipmentNumber) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Shipments.ASSEMBLY_ITEMS, shipmentNumber);
        }
    }

    public static class Cancellations {

        @Step("{method} /" + ApiV2EndPoints.Shipments.CANCELLATIONS)
        public static Response POST(final String shipmentNumber, final String reason) {
            return givenWithAuth()
                    .queryParam("reason", reason)
                    .post(ApiV2EndPoints.Shipments.CANCELLATIONS, shipmentNumber);
        }
    }

    public static class Merge {

        @Step("{method} /" + ApiV2EndPoints.Shipments.MERGE)
        public static Response POST(final String shipmentNumber, final String targetShipmentNumber) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Collections.singletonMap("target_shipment_number", targetShipmentNumber))
                    .post(ApiV2EndPoints.Shipments.MERGE, shipmentNumber);
        }
    }

    /**
     * review[rate]	Да	Оценка
     * review[issue_ids]	-	Список выбранных вариантов ответа
     * review[images_attributes]	-	Прикрепленные изображения
     * review[comment]	-	Комментарий
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class ReviewRequest {
        private final Review review;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class Review {
        private final Integer rate;
        @Singular
        @JsonProperty(value = "issue_ids")
        private final List<Integer> issueIds;
        @JsonProperty(value = "images_attributes")
        private final String imageAttributes;
        private final String comment;
        @JsonProperty(value = "call_back")
        private final Boolean callback;
    }
}
