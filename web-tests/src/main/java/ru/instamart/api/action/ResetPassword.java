package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class ResetPassword {

    @Step("{method} /" + ApiV2EndPoints.Reset.RESET)
    public static Response POST(final String email) {
        return givenCatch()
                .formParams(new HashMap<>(){{put("password_reset[email]", email);}})
                .post(ApiV2EndPoints.Reset.RESET);
    }

    @Step("{method} /" + ApiV2EndPoints.Reset.RESET_PASSWORD)
    public static Response POST(final String token, final String password, final String passwordConformation) {
        final Map<String, Object> data = new HashMap<>();
        data.put("password_reset[reset_password_token]", token);
        data.put("password_reset[password]", password);
        data.put("password_reset[password_confirmation]", passwordConformation);
        return givenCatch()
                .formParams(data)
                .post(ApiV2EndPoints.Reset.RESET_PASSWORD);
    }
}
