package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;

import static io.restassured.RestAssured.given;

public class AuthorizationServiceRequestBase {
    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getAuthorizationServiceRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization", "Bearer " + SessionFactory.getSession(SessionType.AUTHORIZATION_SERVICE).getToken())
                .header("Sbm-Policy-version", "test");
    }

    public static RequestSpecification givenWithAuthAndHeaders(String roles, String baseRealm, Integer identity) {
        return givenWithSpec()
                .header("Authorization", "Bearer " + SessionFactory.getSession(SessionType.AUTHORIZATION_SERVICE).getToken())
                .header("Sbm-Auth-Roles", roles)
                .header("Sbm-Auth-Type", baseRealm)
                .header("Sbm-Auth-Identity", identity)
                .header("Sbm-Policy-version", "test");
    }
}
