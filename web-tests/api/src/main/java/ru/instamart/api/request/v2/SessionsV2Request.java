package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class SessionsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.Sessions.BY_TOKEN)
    public static Response GET(final String token) {
        return givenWithSpec()
                .get(ApiV2EndPoints.Sessions.BY_TOKEN, token);
    }

    public static class User {
        @Step("{method} /" + ApiV2EndPoints.Sessions.USER)
        public static Response GET(final String token) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Sessions.USER, token);
        }
    }
}
