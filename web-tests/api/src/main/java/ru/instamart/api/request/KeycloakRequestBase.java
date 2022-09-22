package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class KeycloakRequestBase {
    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getKeycloakRequestSpec());
    }
}
