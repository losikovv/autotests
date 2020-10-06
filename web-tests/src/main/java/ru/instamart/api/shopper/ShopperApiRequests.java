package instamart.api.shopper;

import instamart.api.common.RestBase;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API SHOPPER
 */
public class ShopperApiRequests {
    static ThreadLocal<String> token = new ThreadLocal<>();

    /**
     * Добавляем спеки к запросу
     */
    private static RequestSpecification givenWithSpec() {
        return given()
                .spec(RestBase.shopperRequestSpec);
    }

    /**
     * Авторизация
     */
    public static Response postSessions(String email, String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(ShopperApiEndpoints.sessions);
    }
}
