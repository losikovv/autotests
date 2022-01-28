package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ShopperAppRequestBase;

@SuppressWarnings("unchecked")
public final class AuthSHPRequest extends ShopperAppRequestBase {

    public static class Login {
        /**
         * Отправка запроса на получение кода
         */
        @Step("{method} /" + ShopperAppEndpoints.Auth.Partners.LOGIN)
        public static Response POST(String phone) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone", phone);
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAppEndpoints.Auth.Partners.LOGIN);
        }
    }

    public static class Code {
        /**
         * Потверрждение телефона
         */
        @Step("{method} /" + ShopperAppEndpoints.Auth.Partners.CODE)
        public static Response POST(String phone, String code) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone", phone);
            requestParams.put("code", code);
            requestParams.put("fingerprint", "test");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAppEndpoints.Auth.Partners.CODE);
        }
    }

    public static class Refresh {
        /**
         * Обновление авторизации
         */
        @Step("{method} /" + ShopperAppEndpoints.Auth.Partners.REFRESH)
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("refresh_token", SessionFactory.getSession(SessionType.SHOPPER_APP).getRefreshToken());
            requestParams.put("fingerprint", "test");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAppEndpoints.Auth.Partners.REFRESH);
        }
    }
}
