package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.jdbc.dao.SpreeUsersDao;

import static io.restassured.RestAssured.given;

public class ApiV1RequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV1RequestSpec());
    }

    public static RequestSpecification givenAdminWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV1RequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("x-csrf-token", SessionFactory.getSession(SessionType.API_V1).getToken())
                .cookies(SessionFactory
                        .getSession(SessionType.API_V1)
                        .getCookies());
    }

    public static RequestSpecification givenWithAuthAndApiKey() {
        return givenWithAuth()
                .header("X-Spree-Token", SpreeUsersDao.INSTANCE.getUserByEmail(SessionFactory.getSession(SessionType.API_V1).getLogin()).getSpreeApiKey());

    }
}
