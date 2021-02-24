package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import instamart.api.enums.v2.AuthProvider;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Authorization {

    @Step("{method} /" + ApiV2EndPoints.Session.SESSIONS)
    public static Response auth(final String email, final String password) {
        return givenCatch()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(ApiV2EndPoints.Session.SESSIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.Session.SESSIONS)
    public static Response authWithClientId(final String email, final String password, final String clientId) {
        return givenCatch()
                .auth()
                .preemptive()
                .basic(email, password)
                .header("Client-Id", clientId)
                .post(ApiV2EndPoints.Session.SESSIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.AuthProviders.SESSIONS)
    public static Response externalAuth(final AuthProvider provider) {
        switch (provider) {
            case METRO:
            case SBERAPP:
                final JSONObject sessionParams = new JSONObject();
                final JSONObject requestParams = new JSONObject();
                sessionParams.put("uid", provider.getSessionUid());
                sessionParams.put("digest", provider.getSessionDigest());
                requestParams.put("session", sessionParams);
                return givenCatch()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
            case VKONTAKTE:
            case FACEBOOK:
                return givenCatch()
                        .param("session[uid]", provider.getSessionUid())
                        .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
            default:
                throw new IllegalStateException("Unexpected value: " + provider.getId());
        }
    }

    @Step("{method} /" + ApiV2EndPoints.Session.SESSIONS_TOKEN)
    public static Response validateSessionToken(final String token) {
        return givenCatch()
                .get(ApiV2EndPoints.Session.SESSIONS_TOKEN, token);
    }

    @Step("{method} /" + ApiV2EndPoints.Session.SESSIONS_USER_TOKEN)
    public static Response getUserDataBYToken(final String token) {
        return givenCatch()
                .get(ApiV2EndPoints.Session.SESSIONS_USER_TOKEN, token);
    }
}
