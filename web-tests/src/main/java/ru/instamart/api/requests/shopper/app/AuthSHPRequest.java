package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.ShopperAppRequestBase;

@SuppressWarnings("unchecked")
public final class AuthSHPRequest extends ShopperAppRequestBase {

    public static class Refresh {
        /**
         * Обновление авторизации
         */
        @Step("{method} /" + ShopperAppEndpoints.Auth.REFRESH)
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("refresh_token", SessionFactory.getSession(SessionType.SHOPPER_APP).getRefreshToken());
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAppEndpoints.Auth.REFRESH);
        }
    }
}
