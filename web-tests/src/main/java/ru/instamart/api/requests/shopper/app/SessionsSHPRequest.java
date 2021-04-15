package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class SessionsSHPRequest {

    /**
     * Авторизация
     */
    @Step("{method} /" + ShopperAppEndpoints.SESSIONS)
    public static Response POST(String login, String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(login, password)
                .post(ShopperAppEndpoints.SESSIONS);
    }
}
