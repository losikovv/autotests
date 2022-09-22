package ru.instamart.api.request.authorization_service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AuthorizationServiceEndpoints;
import ru.instamart.api.model.authorization_service.PolicyModel;
import ru.instamart.api.request.AuthorizationServiceRequestBase;

public class PolicyRequest extends AuthorizationServiceRequestBase {
    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.Policy.POLICY)
    public static Response GET(final String baseRealm) {
        return givenWithAuth()
                .get(AuthorizationServiceEndpoints.Realm.Policy.POLICY, baseRealm);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.Policy.POLICY)
    public static Response PUT(final String baseRealm, final PolicyModel body) {
        return givenWithAuth()
                .body(body)
                .put(AuthorizationServiceEndpoints.Realm.Policy.POLICY, baseRealm);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.Policy.POLICY)
    public static Response PUT(final String baseRealm, final boolean dryRun, final PolicyModel body) {
        return givenWithAuth()
                .queryParam("dry-run", dryRun)
                .body(body)
                .put(AuthorizationServiceEndpoints.Realm.Policy.POLICY, baseRealm);
    }
}
