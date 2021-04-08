package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.endpoints.ShopperApiEndpoints;
import ru.instamart.api.enums.SessionType;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class AuthSHPRequest {

    public static class Refresh {
        /**
         * Обновление авторизации
         */
        @Step("{method} /" + ShopperApiEndpoints.Auth.REFRESH)
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("refresh_token", SessionFactory.getSession(SessionType.SHOPPER).getRefreshToken());
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperApiEndpoints.Auth.REFRESH);
        }
    }
}
