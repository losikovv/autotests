package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.ui.common.pagesdata.UserData;

@SuppressWarnings("unchecked")
public class UserSessionsV1Request extends ApiV1RequestBase {
    /**
     * Авторизация в админке
     */
    @Step("{method} /" + ApiV1Endpoints.USER_SESSIONS)
    public static Response POST(String email, String password) {
        JSONObject requestParams = new JSONObject();
        JSONObject userParams = new JSONObject();
        requestParams.put("user", userParams);
        userParams.put("email", email);
        userParams.put("password", password);
        userParams.put("remember_me", true);
        return givenWithSpec()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.USER_SESSIONS);
    }

    public static Response POST(UserData userData) {
        return POST(userData.getLogin(), userData.getPassword());
    }
}
