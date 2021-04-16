package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.requests.ApiV2RequestBase;

@SuppressWarnings("unchecked")
public final class SessionV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.SESSIONS)
    public static Response POST(final String email, final String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(ApiV2EndPoints.SESSIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.SESSIONS)
    public static Response POST(final String email, final String password, final String clientId) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(email, password)
                .header("Client-Id", clientId)
                .post(ApiV2EndPoints.SESSIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.AuthProviders.SESSIONS)
    public static Response POST(final AuthProviderV2 provider) {
        switch (provider) {
            case METRO:
            case SBERAPP:
                final JSONObject sessionParams = new JSONObject();
                final JSONObject requestParams = new JSONObject();
                sessionParams.put("uid", provider.getSessionUid());
                sessionParams.put("digest", provider.getSessionDigest());
                requestParams.put("session", sessionParams);
                return givenWithSpec()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
            case VKONTAKTE:
            case FACEBOOK:
                return givenWithSpec()
                        .param("session[uid]", provider.getSessionUid())
                        .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
            default:
                throw new IllegalStateException("Unexpected value: " + provider.getId());
        }
    }

    @Step("{method} /" + ApiV2EndPoints.Sessions.BY_TOKEN)
    public static Response GET(final String token) {
        return givenWithSpec()
                .get(ApiV2EndPoints.Sessions.BY_TOKEN, token);
    }

    public static class UserSession {
        @Step("{method} /" + ApiV2EndPoints.Sessions.USER)
        public static Response GET(final String token) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Sessions.USER, token);
        }
    }
}
