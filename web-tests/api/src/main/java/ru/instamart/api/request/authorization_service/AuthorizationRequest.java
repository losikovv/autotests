package ru.instamart.api.request.authorization_service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.AuthorizationServiceEndpoints;
import ru.instamart.api.request.AuthorizationServiceRequestBase;

public class AuthorizationRequest extends AuthorizationServiceRequestBase {
    @Step("{method} /" + AuthorizationServiceEndpoints.Authorization.AUTHORIZATION)
    public static Response POST(final String baseRealm, final JSONObject body) {
        return givenWithAuthAndHeaders("BizdevDept,TestRole", baseRealm, 1)
                .body(body)
                .post(AuthorizationServiceEndpoints.Authorization.AUTHORIZATION);
    }
}