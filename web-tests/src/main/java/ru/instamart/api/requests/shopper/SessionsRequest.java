package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class SessionsRequest {

    /**
     * Авторизация
     */
    @Step("{method} /" + ShopperApiEndpoints.SESSIONS)
    public static Response POST(String login, String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(login, password)
                .post(ShopperApiEndpoints.SESSIONS);
    }
}
