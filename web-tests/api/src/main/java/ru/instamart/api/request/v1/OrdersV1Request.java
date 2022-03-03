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

    @Step("{method} /" + ApiV1Endpoints.Orders.Number.SHIPMENT)
    public static Response PUT(String orderNumber, String shipmentNumber, Shipment shipment) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(shipment)
                .put(ApiV1Endpoints.Orders.Number.SHIPMENT, orderNumber, shipmentNumber);
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
}
