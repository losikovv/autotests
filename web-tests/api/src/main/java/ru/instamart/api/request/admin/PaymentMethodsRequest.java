package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Map;

public final class PaymentMethodsRequest extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.PAYMENT_METHODS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.PAYMENT_METHODS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.PaymentMethods.UPDATE_POSITIONS)
    public static Response POST() {

        return givenWithAuth()
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.Admin.PaymentMethods.UPDATE_POSITIONS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.PaymentMethods.UPDATE_POSITIONS)
    public static Response POST(Map<String, Integer> methodIdsAndPositions) {
        JSONObject body = new JSONObject();
        JSONObject positions = new JSONObject();

        methodIdsAndPositions.forEach((key, value) -> positions.put(key, value));

        body.put("positions", positions);

        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.PaymentMethods.UPDATE_POSITIONS);
    }
}
