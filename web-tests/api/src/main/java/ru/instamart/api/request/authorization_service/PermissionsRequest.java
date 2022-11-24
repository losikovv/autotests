package ru.instamart.api.request.authorization_service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AuthorizationServiceEndpoints;
import ru.instamart.api.request.AuthorizationServiceRequestBase;

public class PermissionsRequest extends AuthorizationServiceRequestBase {
    @Step("{method} /" + AuthorizationServiceEndpoints.Permissions.PERMISSION)
    public static Response GET(final String baseRealm, final String resource) {
        return givenWithAuthAndHeaders("bizdev_dept,test_role", baseRealm, 1)
                .queryParam("resource", resource)
                .get(AuthorizationServiceEndpoints.Permissions.PERMISSION);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Permissions.PERMISSION)
    public static Response GET(final String baseRealm, final String resource, final String role) {
        return givenWithAuthAndHeaders(role, baseRealm, 1)
                .queryParam("resource", resource)
                .get(AuthorizationServiceEndpoints.Permissions.PERMISSION);
    }
}