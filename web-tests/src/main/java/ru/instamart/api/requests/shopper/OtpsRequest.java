package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class OtpsRequest {

    public static class Tokens {
        /**
         * Отправляем запрос для получения смс с кодом
         */
        @Step("{method} /" + ShopperApiEndpoints.Otps.TOKENS)
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone","79588128783");
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
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("phone","79588128783");
            requestParams.put("code","111111");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperApiEndpoints.Otps.AUTHORIZATIONS);
        }
    }
}
