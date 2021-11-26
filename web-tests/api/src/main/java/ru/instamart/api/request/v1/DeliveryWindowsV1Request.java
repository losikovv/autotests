package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.enums.v2.ShippingMethodsV2;
import ru.instamart.api.request.ApiV1RequestBase;

public class DeliveryWindowsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.DeliveryWindows.BY_ID)
    public static Response PUT(Long deliveryWindowId, long number) {
        JSONObject body = new JSONObject();
        JSONObject deliveryWindow = new JSONObject();
        deliveryWindow.put("shipment_max_kilos", number);
        deliveryWindow.put("shipments_excess_kilos", number);
        deliveryWindow.put("shipments_limit", number);
        deliveryWindow.put("shipments_excess_items_count", number);
        deliveryWindow.put("kind", ShippingMethodsV2.PICKUP.getMethod());
        body.put("delivery_window", deliveryWindow);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.DeliveryWindows.BY_ID, deliveryWindowId);
    }
}
