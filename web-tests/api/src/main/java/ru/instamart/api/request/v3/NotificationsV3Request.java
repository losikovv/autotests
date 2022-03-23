package ru.instamart.api.request.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;
import ru.sbermarket.common.Mapper;

import java.util.List;
import java.util.Objects;

public class NotificationsV3Request extends ApiV3RequestBase {


    public static Response POST(String orderId, String type) {
        return POST(orderId, type, "5548", 1, 1);
    }

    public static Response POST(String orderId, String type, String retailerSku, Integer originalQuantity, Integer quantity) {
        return POST(orderId, type, retailerSku, originalQuantity, quantity, "");
    }

    public static Response POST(String orderId, String type, String retailerSku, Integer originalQuantity, Integer quantity, String weight) {
        return POST(orderId, type, retailerSku, originalQuantity, quantity, weight, "+799911111111",
                "2021-05-24T13:00:00+03:00", "2021-05-24T16:00:00+03:00");
    }
    @Step("{method} /" + ApiV3Endpoints.NOTIFICATIONS)
    public static Response POST(String orderId,
                                String type,
                                String retailerSku,
                                Integer originalQuantity,
                                Integer quantity,
                                String weight,
                                String phone,
                                String expectedFrom,
                                String expectedTo) {
        JSONObject requestParams = new JSONObject();
        JSONObject event = new JSONObject();
        JSONObject payload = new JSONObject();
        JSONObject order = new JSONObject();
        JSONObject customer = new JSONObject();
        JSONObject delivery = new JSONObject();
        JSONArray positions = new JSONArray();
        JSONObject position = new JSONObject();

        requestParams.put("event", event);

        if (Objects.nonNull(type)) event.put("type", type);
        event.put("payload", payload);

        if (Objects.nonNull(orderId)) payload.put("order_id", orderId);
        payload.put("order", order);

        if (Objects.nonNull(orderId)) order.put("originalOrderId", orderId);
        order.put("customer", customer);
        order.put("delivery", delivery);
        order.put("positions", positions);

        if (Objects.nonNull(phone)) customer.put("phone", phone);

        if (Objects.nonNull(expectedFrom)) delivery.put("expectedFrom", expectedFrom);
        if (Objects.nonNull(expectedTo)) delivery.put("expectedTo", expectedTo);

        positions.add(position);
        if (Objects.nonNull(retailerSku)) position.put("id", retailerSku);
        if (Objects.nonNull(originalQuantity)) position.put("originalQuantity", originalQuantity);
        if (Objects.nonNull(quantity)) position.put("quantity", quantity);
        if (Objects.nonNull(weight)) position.put("weight", weight);

        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .contentType(ContentType.JSON)
                .body(requestParams)
                .post(ApiV3Endpoints.NOTIFICATIONS);
    }

    @Step("{method} /" + ApiV3Endpoints.NOTIFICATIONS)
    public static Response POST(String orderId, String type, String name) {
        JSONObject requestParams = new JSONObject();
        JSONObject event = new JSONObject();
        JSONObject payload = new JSONObject();
        JSONObject order = new JSONObject();
        JSONObject customer = new JSONObject();
        JSONObject delivery = new JSONObject();
        JSONArray positions = new JSONArray();
        JSONObject position = new JSONObject();
        JSONObject total = new JSONObject();

        requestParams.put("event", event);

        event.put("type", type);
        event.put("payload", payload);

        payload.put("order_id", orderId);
        payload.put("order", order);

        order.put("originalOrderId", orderId);
        order.put("customer", customer);
        order.put("delivery", delivery);
        order.put("positions", positions);
        order.put("total", total);

        customer.put("name", name);
        customer.put("phone", "+799911111111");

        delivery.put("expectedFrom", "2021-05-24T13:00:00+03:00");
        delivery.put("expectedTo", "2021-05-24T16:00:00+03:00");

        positions.add(position);
        position.put("id", "5548");
        position.put("originalQuantity", 1);
        position.put("quantity", 1);
        position.put("price", "500");
        position.put("discountPrice", "0");
        position.put("replacedByID", "");
        position.put("weight", "700");
        position.put("totalPrice", "1124.55");
        position.put("totalDiscountPrice", "1250.00");

        total.put("totalPrice", "500");
        total.put("discountTotalPrice", "500");

        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .contentType(ContentType.JSON)
                .body(requestParams)
                .post(ApiV3Endpoints.NOTIFICATIONS);
    }

    @Step("{method} /" + ApiV3Endpoints.NOTIFICATIONS)
    public static Response POST(NotificationsV3Request.Notifications notifications) {

        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .contentType(ContentType.JSON)
                .body(Mapper.INSTANCE.objectToString(notifications))
                .post(ApiV3Endpoints.NOTIFICATIONS);
    }

    @Data
    @Builder
    public static final class Notifications {
        private Event event;
    }

    @Data
    @Builder
    public static final class Event {
        private String type;
        private Payload payload;
    }

    @Data
    @Builder
    public static final class Payload {
        @JsonProperty(value = "order_id")
        private String orderId;
        private Order order;
    }

    @Data
    @Builder
    public static final class Order {
        private String originalOrderId;
        private Customer customer;
        private Delivery delivery;
        private List<Position> positions;
        private Total total;
    }

    @Data
    @Builder
    public static final class Customer {
        private String name;
        private String phone;
    }

    @Data
    @Builder
    public static final class Delivery {
        private String expectedFrom;
        private String expectedTo;
    }

    @Data
    @Builder
    public static final class Position {
        private String id;
        private Integer originalQuantity;
        private Integer quantity;
        private String price;
        private String discountPrice;
        private String replacedByID;
        private String weight;
        private String totalPrice;
        private String totalDiscountPrice;
    }

    @Data
    @Builder
    public static final class Total {
        private String totalPrice;
        private String discountTotalPrice;
    }
}
