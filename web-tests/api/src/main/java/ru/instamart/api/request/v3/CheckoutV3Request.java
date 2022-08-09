package ru.instamart.api.request.v3;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

import java.util.List;

public class CheckoutV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Checkout.ORDER)
    public static Response GET(String orderNumber) {
        return givenWithAuth(ClientV3.SBERMARKET_WEB)
                .get(ApiV3Endpoints.Checkout.ORDER, orderNumber);
    }

    public static class Initialization {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.INITIALIZATION)
        public static Response POST(String orderNumber, List<String> shipmentNumbers) {
            JSONObject body = new JSONObject();
            body.put("shipment_numbers", shipmentNumbers);
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV3Endpoints.Checkout.Orders.INITIALIZATION, orderNumber);
        }
    }

    public static class Completion {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.COMPLETION)
        public static Response POST(String orderNumber, List<String> shipmentNumbers) {
            JSONObject body = new JSONObject();
            body.put("shipment_numbers", shipmentNumbers);
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV3Endpoints.Checkout.Orders.COMPLETION, orderNumber);
        }
    }

    public static class PaymentTools {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.PAYMENT_TOOLS)
        public static Response GET(String orderNumber) {
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .get(ApiV3Endpoints.Checkout.Orders.PAYMENT_TOOLS, orderNumber);
        }
    }

    public static class Promotions {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.PROMOTIONS)
        public static Response POST(String orderNumber, String promotionCode, List<String> shipmentNumbers) {
            JSONObject body = new JSONObject();
            body.put("promotion_code", promotionCode);
            body.put("shipment_numbers", shipmentNumbers);
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV3Endpoints.Checkout.Orders.PROMOTIONS, orderNumber);
        }
    }

    public static class PromotionRemove {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.Promotions.REMOVE)
        public static Response POST(String orderNumber, String promotionCode, List<String> shipmentNumbers) {
            JSONObject body = new JSONObject();
            body.put("shipment_numbers", shipmentNumbers);
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV3Endpoints.Checkout.Orders.Promotions.REMOVE, orderNumber, promotionCode);
        }
    }

    public static class ReplacementPolicies {
        @Step("{method} /" + ApiV3Endpoints.Checkout.REPLACEMENT_POLICIES)
        public static Response GET() {
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .get(ApiV3Endpoints.Checkout.REPLACEMENT_POLICIES);
        }
    }

    public static class Shipments {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Shipments.SHIPPING_RATES)
        public static Response GET(String shipmentNumber, String date) {
            RequestSpecification req = givenWithAuth(ClientV3.SBERMARKET_WEB);
            if (date != null) req.queryParam("date", date);
            return req.get(ApiV3Endpoints.Checkout.Shipments.SHIPPING_RATES, shipmentNumber);
        }
    }

    public static class Validation {
        @Step("{method} /" + ApiV3Endpoints.Checkout.Orders.VALIDATION)
        public static Response GET(String orderNumber) {
            return givenWithAuth(ClientV3.SBERMARKET_WEB)
                    .get(ApiV3Endpoints.Checkout.Orders.VALIDATION, orderNumber);
        }
    }

    @Step("{method} /" + ApiV3Endpoints.Checkout.ORDER)
    public static Response PUT(OrderRequest order, String orderNumber) {
        return givenWithAuth(ClientV3.SBERMARKET_WEB)
                .contentType(ContentType.JSON)
                .body(order)
                .put(ApiV3Endpoints.Checkout.ORDER, orderNumber);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrderRequest {
        private Order order;
        @JsonProperty("shipment_numbers")
        private List<String> shipmentNumbers;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Order {
        private String phone;
        private String email;
        @JsonProperty("replacement_policy_id")
        private Long replacementPolicyId;
        @JsonProperty("address_attributes")
        private AddressAttributes addressAttributes;
        @JsonProperty("payment_attributes")
        private PaymentAttributes paymentAttributes;
        @JsonProperty("shipments_attributes")
        private List<ShipmentsAttributes> shipmentsAttributes;
        @JsonProperty("shipping_method")
        private ShippingMethod shippingMethod;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class AddressAttributes {
        private Long id;
        private String apartment;
        private String entrance;
        private String floor;
        @JsonProperty("door_phone")
        private String doorPhone;
        @JsonProperty("delivery_to_door")
        private Boolean deliveryToDoor;
        private String comments;
        private String instructions;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PaymentAttributes {
        @JsonProperty("payment_tool_id")
        private Long paymentToolId;
        @JsonProperty("sber_spasibo_amount")
        private Integer sberSpasiboAmount;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ShipmentsAttributes {
        private String number;
        @JsonProperty("delivery_window_id")
        private Integer deliveryWindowId;
        @JsonProperty("delivery_window_kind")
        private String deliveryWindowKind;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ShippingMethod {
        private String kind;
        @JsonProperty("ship_address_id")
        private Integer shipAddressId;
        @JsonProperty("pickup_store_id")
        private Integer pickupStoreId;
    }
}
