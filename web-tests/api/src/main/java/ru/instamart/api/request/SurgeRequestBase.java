package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class SurgeRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getSurgeRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJyb2xlcyI6WyJ0cmFuc3BvcnRfZGVwdCJdfQ.ywu9rd8YlqbPmzw6QFZ9klhfLkKCggER13r6SuvaBnA");
    }
}
