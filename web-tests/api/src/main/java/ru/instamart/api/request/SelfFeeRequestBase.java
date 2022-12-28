package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;

import static io.restassured.RestAssured.given;

public class SelfFeeRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiSelfFeeRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("sbm-forward-feature-version-stf", "s-sb-stfdefault-backend-sbermarket")
                .cookies(SessionFactory
                        .getSession(SessionType.ADMIN)
                        .getCookies());
    }
}
