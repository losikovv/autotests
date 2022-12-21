package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.helper.ShadowcatHelper;

import static io.restassured.RestAssured.given;

public class ShadowcatRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getShadowcatRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .contentType("application/json")
                .header("Authorization",
                        ShadowcatHelper.getInstance().getJwtToken());
    }
}
