package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;
import static ru.instamart.api.helper.ApiV3Helper.getApiClientToken;

public class ApiV3RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }

    public static RequestSpecification givenMetroMarketPlace() {
        String token = getApiClientToken("metro_marketplace");
        return given()
                .header("Client-Token", token)
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenAuchan() {
        String token = getApiClientToken("auchan");
        return given()
                .header("Client-Token", token)
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        String token = getApiClientToken("sber_devices");
        return givenWithSpec()
                .header("Api-Version","3.0")
                .header("Client-Token", token);
    }
}
