package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class SessionsSHPRequest extends ShopperAppRequestBase {

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
