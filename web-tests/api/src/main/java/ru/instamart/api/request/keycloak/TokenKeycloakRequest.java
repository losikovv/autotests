package ru.instamart.api.request.keycloak;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.KeycloakEndpoints;
import ru.instamart.api.request.KeycloakRequestBase;

public class TokenKeycloakRequest extends KeycloakRequestBase {
    @Step("{method} /" + KeycloakEndpoints.TOKEN)
    public static Response POST(final String keycloakRealm, final String clientId, final String clientSecret) {
        return givenWithSpec()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("grant_type", "client_credentials")
                .post(KeycloakEndpoints.TOKEN, keycloakRealm);
    }
}
