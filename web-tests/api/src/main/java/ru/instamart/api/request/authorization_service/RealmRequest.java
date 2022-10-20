package ru.instamart.api.request.authorization_service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.AuthorizationServiceEndpoints;
import ru.instamart.api.model.authorization_service.RealmModel;
import ru.instamart.api.request.AuthorizationServiceRequestBase;

public class RealmRequest extends AuthorizationServiceRequestBase {
    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM)
    public static Response GET() {
        return givenWithAuth()
                .get(AuthorizationServiceEndpoints.Realm.REALM);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM_FULL)
    public static Response GET(final String realm) {
        return givenWithAuth()
                .get(AuthorizationServiceEndpoints.Realm.REALM_FULL, realm);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM)
    public static Response POST(final RealmModel body) {
        return givenWithAuth()
                .body(body)
                .post(AuthorizationServiceEndpoints.Realm.REALM);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM)
    public static Response POST(final JSONObject body) {
        return givenWithAuth()
                .body(body)
                .post(AuthorizationServiceEndpoints.Realm.REALM);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM)
    public static Response POST(final boolean dryRun, final RealmModel body) {
        return givenWithAuth()
                .queryParam("dry-run", dryRun)
                .body(body)
                .post(AuthorizationServiceEndpoints.Realm.REALM);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM_FULL)
    public static Response PUT(final String realm, final RealmModel body) {
        return givenWithAuth()
                .body(body)
                .contentType(ContentType.JSON)
                .put(AuthorizationServiceEndpoints.Realm.REALM_FULL, realm);
    }

    @Step("{method} /" + AuthorizationServiceEndpoints.Realm.REALM_FULL)
    public static Response PUT(final String realm, final boolean dryRun, final RealmModel body) {
        return givenWithAuth()
                .queryParam("dry-run", dryRun)
                .body(body)
                .contentType(ContentType.JSON)
                .put(AuthorizationServiceEndpoints.Realm.REALM_FULL, realm);
    }

}