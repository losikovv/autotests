package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class EtaRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given().spec(Specification.INSTANCE.getEtaRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec();
    }
}
