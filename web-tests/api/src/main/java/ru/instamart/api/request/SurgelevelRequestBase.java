package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.helper.SurgeLevelHelper;

import static io.restassured.RestAssured.given;

public class SurgelevelRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given().spec(Specification.INSTANCE.getSurgelevelRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec().header("x-api-secret", SurgeLevelHelper.getInstance().getSurgeAuthToken());
    }
}
