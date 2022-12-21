package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.model.shadowcat.OrderSC;
import ru.instamart.api.request.ShadowcatRequestBase;

public class CalculateRequest extends ShadowcatRequestBase {
    @Step("{method} /"+ ShadowcatEndpoints.CALCULATE)
    public static Response POST(OrderSC order) {
        return givenWithAuth()
                .body(order)
                .post(ShadowcatEndpoints.CALCULATE);
    }
}