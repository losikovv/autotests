package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.testdata.UserData;

@SuppressWarnings("unchecked")
public final class AuthProvidersV2Request extends ApiV2RequestBase {

    public static class Sessions {

        public static Response POST(final AuthProviderV2 provider) {
            return POST(provider, null, null, null, null, null);
        }

        public static Response POST(final AuthProviderV2 provider, final String uid) {
            return POST(provider, null, null, null, null, uid);
        }

        public static Response POST(final AuthProviderV2 provider, final UserData userData, final String uid) {
            return POST(provider,
                    userData.getFirstName(),
                    userData.getLastName(),
                    userData.getLogin(),
                    userData.getPhone(),
                    uid);
        }

        @Step("{method} /" + ApiV2EndPoints.AuthProviders.SESSIONS)
        public static Response POST(final AuthProviderV2 provider,
                                    final String firstName,
                                    final String lastName,
                                    final String email,
                                    final String phone,
                                    final String uid) {
            switch (provider) {
                case METRO:
                case SBERAPP:
                    final JSONObject requestParams = new JSONObject();
                    final JSONObject session = new JSONObject();
                    requestParams.put("session", session);
                    session.put("uid", uid != null ? uid : provider.getSessionUid());
                    session.put("digest", provider.getSessionDigest());
                    if (firstName != null) session.put("first_name", firstName);
                    if (lastName != null) session.put("last_name", lastName);
                    if (email != null) session.put("email", email);
                    if (phone != null) session.put("phone", phone);
                    return givenWithSpec()
                            .body(requestParams)
                            .contentType(ContentType.JSON)
                            .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
                case VKONTAKTE:
                case FACEBOOK:
                case WRONG_ID:
                    RequestSpecification request = givenWithSpec();
                    if (firstName != null) request.param("session[first_name]", firstName);
                    if (lastName != null) request.param("session[last_name]", lastName);
                    if (email != null) request.param("session[email]", email);
                    if (phone != null) request.param("session[phone]", phone);
                    return request
                            .param("session[uid]", uid != null ? uid : provider.getSessionUid())
                            .post(ApiV2EndPoints.AuthProviders.SESSIONS, provider.getId());
                default:
                    throw new IllegalStateException("Unexpected value: " + provider.getId());
            }
        }
    }
}
