package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class AdjustmentsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.ADJUSTMENTS)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Orders.Number.ADJUSTMENTS, orderNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID)
    public static Response GET(String orderNumber, Long adjustmentId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID, orderNumber, adjustmentId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID)
    public static Response PUT(String orderNumber, Long adjustmentId, int amount, String label) {
        JSONObject body = new JSONObject();
        JSONObject adjustment = new JSONObject();
        adjustment.put("amount", amount);
        adjustment.put("label", label);
        body.put("adjustment", adjustment);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID, orderNumber, adjustmentId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID)
    public static Response DELETE(String orderNumber, Long adjustmentId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.Orders.Number.Adjustments.ID, orderNumber, adjustmentId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.TOGGLE_STATE)
    public static Response POST(String orderNumber, Long adjustmentId) {
        return givenWithAuth()
                .post(ApiV1Endpoints.Admin.Orders.Number.Adjustments.TOGGLE_STATE, orderNumber, adjustmentId);
    }

    public static class Open {
        @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.OPEN)
        public static Response POST(String orderNumber) {
            return givenWithAuth()
                    .post(ApiV1Endpoints.Admin.Orders.Number.Adjustments.OPEN, orderNumber);
        }
    }

    public static class Close {
        @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.Adjustments.CLOSE)
        public static Response POST(String orderNumber) {
            return givenWithAuth()
                    .post(ApiV1Endpoints.Admin.Orders.Number.Adjustments.CLOSE, orderNumber);
        }
    }
}
