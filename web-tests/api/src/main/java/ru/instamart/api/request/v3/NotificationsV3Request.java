package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class NotificationsV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.NOTIFICATIONS)
    public static Response POST(String orderId, String type) {
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

        customer.put("name", "John Snow");
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

        return givenMetroMarketPlace()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .post(ApiV3Endpoints.NOTIFICATIONS);
    }
}
