package ru.instamart.api.requests;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class ApiV3RequestBase {
    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        //todo реализовать тут апи в3 авторизацию
        return givenWithSpec();
    }
}
