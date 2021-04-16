package ru.instamart.api.requests;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;

import static io.restassured.RestAssured.given;

public class ApiV1RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV1RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .cookies(SessionFactory
                        .getSession(SessionType.API_V1)
                        .getCookies());
    }
}
