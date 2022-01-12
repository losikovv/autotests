package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class ApiV3RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }

    public static RequestSpecification givenMetroMarketPlace() {
        return given()
                .header("Client-Token", "8055cfd11c887f2887dcd109e66dd166")
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenAuchan() {
        //todo проверить наличие токена для Ашан на тестовых средах
        return given()
                .header("Client-Token", "9d89792b-1341-45de-974f-3632e9896909")
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        //todo реализовать тут апи в3 авторизацию
        return givenWithSpec();
    }
}
