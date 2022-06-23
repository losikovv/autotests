package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class OrderCancellationsAdminV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.CANCELLATIONS)
    public static Response GET(String orderNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Orders.Number.CANCELLATIONS, orderNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Orders.Number.CANCELLATIONS)
    public static Response POST(String orderNumber, int reasonId, String reasonDetails) {
        JSONObject body = new JSONObject();
        JSONObject cancellation = new JSONObject();
        cancellation.put("reason_id", reasonId);
        if (Objects.nonNull(reasonDetails)) cancellation.put("reason_details", reasonDetails);
        body.put("cancellation", cancellation);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.Orders.Number.CANCELLATIONS, orderNumber);
    }
}
