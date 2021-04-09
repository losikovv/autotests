package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import java.util.HashMap;
import java.util.Map;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class ResetPasswordV2Request {

    @Step("{method} /" + ApiV2EndPoints.Passwords.RESET)
    public static Response POST(final String email) {
        return givenCatch()
                .formParams(new HashMap<>(){{put("password_reset[email]", email);}})
                .post(ApiV2EndPoints.Passwords.RESET);
    }

    @Step("{method} /" + ApiV2EndPoints.PASSWORDS)
    public static Response POST(final String token, final String password, final String passwordConformation) {
        final Map<String, Object> data = new HashMap<>();
        data.put("password_reset[reset_password_token]", token);
        data.put("password_reset[password]", password);
        data.put("password_reset[password_confirmation]", passwordConformation);
        return givenCatch()
                .formParams(data)
                .post(ApiV2EndPoints.PASSWORDS);
    }
}
