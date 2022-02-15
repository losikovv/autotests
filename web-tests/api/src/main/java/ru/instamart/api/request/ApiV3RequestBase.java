package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;
import static ru.instamart.api.helper.ApiV3Helper.getApiClienToken;

public class ApiV3RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }

    public static RequestSpecification givenMetroMarketPlace() {
        String token = getApiClienToken("metro_marketplace");
        return given()
                .header("Client-Token", token)
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenAuchan() {
        String token = getApiClienToken("auchan");
        return given()
                .header("Client-Token", token)
                .spec(Specification.INSTANCE.getApiV3RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        String token = getApiClienToken("sber_devices");
        return givenWithSpec()
                .header("Api-Version","3.0")
                .header("Client-Token", token);
    }
}
