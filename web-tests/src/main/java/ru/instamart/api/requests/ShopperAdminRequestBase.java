package ru.instamart.api.requests;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;

import static io.restassured.RestAssured.given;

public class ShopperAdminRequestBase {

    /**
     * Добавляем спеки к запросу
     */
    public static RequestSpecification givenWithSpec() {
        return given().spec(Specification.INSTANCE.getShopperRequestSpec());
    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec().header(
                "Authorization",
                "Token " + SessionFactory.getSession(SessionType.SHOPPER_ADMIN).getToken());
    }
}
