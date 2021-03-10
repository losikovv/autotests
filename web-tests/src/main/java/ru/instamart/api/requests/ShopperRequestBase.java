package instamart.api.requests;

import instamart.api.SessionFactory;
import instamart.api.common.Specification;
import instamart.api.enums.SessionType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ShopperRequestBase {

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
                "Token " + SessionFactory.getSession(SessionType.SHOPPER).getToken());
    }
}
