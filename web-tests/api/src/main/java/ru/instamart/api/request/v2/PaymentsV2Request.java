package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class PaymentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Payments.CREDIT_CARD_AUTHORIZATION)
    public static Response POST(String orderNumber) {
        JSONObject requestParams = new JSONObject();
        JSONObject creditCardAuthorization = new JSONObject();
        creditCardAuthorization.put("order_number", orderNumber);
        requestParams.put("credit_card_authorization", creditCardAuthorization);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.Payments.CREDIT_CARD_AUTHORIZATION);
    }
}
