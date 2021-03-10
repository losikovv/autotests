package instamart.api.requests.shopper;

import instamart.api.SessionFactory;
import instamart.api.endpoints.ShopperApiEndpoints;
import instamart.api.enums.SessionType;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static instamart.api.requests.ShopperRequestBase.givenWithSpec;

public final class AuthRequest {

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
