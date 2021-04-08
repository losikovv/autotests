package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class SessionsSHPRequest {

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
