package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;

import static io.restassured.RestAssured.given;

public class AdminRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiAdminRequestSpec());
    }

    /**
     * Добавляем куки сессии к запросу
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec().log().all()
                .formParam("utf-8", "✓")
                .formParam("authenticity_token", SessionFactory.getSession(SessionType.ADMIN).getToken())
                .cookies(SessionFactory
                        .getSession(SessionType.ADMIN)
                        .getCookies());
    }

    public static RequestSpecification givenWithAuthAndSpa() {
        return givenWithAuth()
                .basePath("spa/");
    }
}
