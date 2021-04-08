package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class OtpsSHPRequest {

    public static class Tokens {
        /**
         * Отправляем запрос для получения смс с кодом
         */
        @Step("{method} /" + ShopperApiEndpoints.Otps.TOKENS)
        public static Response POST(final String phone) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone", phone);
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperApiEndpoints.Otps.TOKENS);
        }
    }

    public static class Authorizations {
        /**
         * Авторизация по номеру телефона и коду из смс
         */
        @Step("{method} /" + ShopperApiEndpoints.Otps.AUTHORIZATIONS)
        public static Response POST(final String phone, final String code) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone", phone);
            requestParams.put("code", code);
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperApiEndpoints.Otps.AUTHORIZATIONS);
        }
    }
}
