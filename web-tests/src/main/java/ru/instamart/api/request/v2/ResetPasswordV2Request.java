package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.HashMap;
import java.util.Map;

public final class ResetPasswordV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Passwords.RESET)
    public static Response POST(final String email) {
        return givenWithSpec()
                .formParams(new HashMap<>(){{put("password_reset[email]", email);}})
                .post(ApiV2EndPoints.Passwords.RESET);
    }

    @Step("{method} /" + ApiV2EndPoints.PASSWORDS)
    public static Response POST(final String token, final String password, final String passwordConformation) {
        final Map<String, Object> data = new HashMap<>();
        data.put("password_reset[reset_password_token]", token);
        data.put("password_reset[password]", password);
        data.put("password_reset[password_confirmation]", passwordConformation);
        return givenWithSpec()
                .formParams(data)
                .post(ApiV2EndPoints.PASSWORDS);
    }
}
