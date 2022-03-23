package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.v3.ClientV3;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static ru.instamart.api.helper.ApiV3Helper.getApiClientToken;

public class ApiV3RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenWithAuth(ClientV3 client) {
        RequestSpecification req = given().spec(Specification.INSTANCE.getApiV3RequestSpec());
        if (Objects.nonNull(client)) req.header("Client-Token", getApiClientToken(client));
        return req;
    }

    public static RequestSpecification givenWithAuth(String token) {
        return given()
                .header("Client-Token", token)
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }
}
