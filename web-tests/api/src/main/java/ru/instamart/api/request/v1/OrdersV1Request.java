package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.List;

public class OrdersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.ORDERS)
    public static Response GET(Integer pageNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.ORDERS, pageNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.Orders.NUMBER)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Orders.NUMBER, orderNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.Orders.Number.SHIPMENT)
    public static Response PUT(String orderNumber, String shipmentNumber, Shipment shipment) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(shipment)
                .put(ApiV1Endpoints.Orders.Number.SHIPMENT, orderNumber, shipmentNumber);
    }

    public static class Compensations {
        @Step("{method} /" + ApiV1Endpoints.Orders.Number.Compensations.NEW)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.Compensations.NEW, orderNumber);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.COMPENSATIONS)
        public static Response POST(String orderNumber, Compensation compensation) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(compensation)
                    .post(ApiV1Endpoints.Orders.Number.COMPENSATIONS, orderNumber);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.Compensations.ID)
        public static Response GET(String orderNumber, Long compensationId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.Compensations.ID, orderNumber, compensationId);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.Compensations.ID)
        public static Response PUT(String orderNumber, Long compensationId, Compensation compensation) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(compensation)
                    .put(ApiV1Endpoints.Orders.Number.Compensations.ID, orderNumber, compensationId);
        }
    }

    public static class LineItems {
        @Step("{method} /" + ApiV1Endpoints.Orders.Number.LINE_ITEMS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .queryParam("order_number", orderNumber)
                    .get(ApiV1Endpoints.Orders.Number.LINE_ITEMS, orderNumber);
        }
    }

    public static class MergeStatus {
        @Step("{method} /" + ApiV1Endpoints.Orders.Number.MERGE_STATUS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.MERGE_STATUS, orderNumber);
        }
    }

    public static class Payments {
        @Step("{method} /" + ApiV1Endpoints.Orders.Number.PAYMENTS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.PAYMENTS, orderNumber);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.PAYMENTS)
        public static Response POST(String orderNumber, Long paymentToolId) {
            JSONObject body = new JSONObject();
            body.put("payment_tool_id", paymentToolId);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Orders.Number.PAYMENTS, orderNumber);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.Payments.ID)
        public static Response GET(String orderNumber, Long paymentId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.Payments.ID, orderNumber, paymentId);
        }

        @Step("{method} /" + ApiV1Endpoints.Orders.Number.Payments.PURCHASE)
        public static Response PUT(String orderNumber, Long paymentId) {
            return givenWithAuth()
                    .put(ApiV1Endpoints.Orders.Number.Payments.PURCHASE, orderNumber, paymentId);
        }
    }

    public static class PaymentTools {
        @Step("{method} /" + ApiV1Endpoints.Orders.Number.PAYMENT_TOOLS)
        public static Response GET(String orderNumber) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Orders.Number.PAYMENT_TOOLS, orderNumber);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ShipmentParams {
        @JsonProperty(value = "store_id")
        private Integer storeId;
        @JsonProperty(value = "delivery_window_id")
        private Integer deliveryWindowId;
        @JsonProperty(value = "change_reason_id")
        private Integer changeReasonId;
        @JsonProperty(value = "assembly_comment")
        private String assemblyComment;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Shipment {
        private ShipmentParams shipment;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CompensationParams {
        private String email;
        private String comment;
        @JsonProperty("email_body")
        private String emailBody;
        private Long reason;
        @JsonProperty("promo_type")
        private Long promoType;
        @JsonProperty("order_line_item_ids")
        private List<Long> orderLineItemIds;
        @JsonProperty("promotion_id")
        private Long promotionId;
        private Approvement approvement;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Compensation {
        private CompensationParams compensation;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Approvement {
        private String comment;
    }
}
