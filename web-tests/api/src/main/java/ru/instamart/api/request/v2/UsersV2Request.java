package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.HashMap;
import java.util.Map;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;

public final class UsersV2Request extends ApiV2RequestBase {

    private UsersV2Request() {}

    @Step("{method} /" + ApiV2EndPoints.Users.BY_EMAIL)
    public static Response GET(final String email, final boolean isAuth) {
        if (isAuth) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.Users.BY_EMAIL, email);
        } else {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Users.BY_EMAIL, email);
        }
    }

    @Step("{method} /" + ApiV2EndPoints.Users.BY_EMAIL)
    public static Response GET(final String email, final boolean isAuth, final String token) {
        if (isAuth) {
            return givenCustomToken(token)
                    .get(ApiV2EndPoints.Users.BY_EMAIL, email);
        } else {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Users.BY_EMAIL, email);
        }
    }

    @Step("{method} /" + ApiV2EndPoints.Users.BY_EMAIL)
    public static Response GET(final String clientId, final String email) {
        return givenWithAuth()
                .header("Client-Id", clientId)
                .get(ApiV2EndPoints.Users.BY_EMAIL, email);
    }

    @Step("{method} /" + ApiV2EndPoints.Users.BY_EMAIL)
    public static Response PUT(
                               final String email,
                               final String firstName,
                               final String lastName,
                               final boolean promo) {
        checkFieldIsNotEmpty(email, "email");

        final Map<String, Object> data = new HashMap<>();
        if (firstName != null && !firstName.isEmpty()) data.put("user[first_name]", firstName);
        if (lastName != null && !lastName.isEmpty()) data.put("user[last_name]", lastName);
        data.put("user[promo_terms_accepted]", promo);
        return givenWithAuth()
                .formParams(data)
                .put(ApiV2EndPoints.Users.BY_EMAIL, email);
    }

    @Step("{method} /" + ApiV2EndPoints.Users.BY_EMAIL)
    public static Response PUT(final String email,
                               final String currentPassword,
                               final String password,
                               final String passwordConfirmation) {
        checkFieldIsNotEmpty(email, "email");

        final Map<String, Object> data = new HashMap<>();
        data.put("user[current_password]", currentPassword);
        data.put("user[password]", password);
        data.put("user[password_confirmation]", passwordConfirmation);
        return givenWithAuth()
                .formParams(data)
                .put(ApiV2EndPoints.Users.BY_EMAIL, email);
    }


    @Step("{method} /" + ApiV2EndPoints.USERS)
    public static Response POST(
            final String email,
            final String firstName,
            final String lastName,
            final String password) {
        final Map<String, Object> data = new HashMap<>();
        data.put("user[email]", email);
        data.put("user[first_name]", firstName);
        data.put("user[last_name]", lastName);
        data.put("user[password]", password);

        return givenWithSpec()
                .formParams(data)
                .post(ApiV2EndPoints.USERS);
    }

    @Step("{method} /" + ApiV2EndPoints.USERS)
    public static Response POST(
            final String email,
            final String firstName,
            final String lastName,
            final String password,
            final String location,
            final boolean terms,
            final boolean promoTerms,
            final int anonymousId,
            final boolean b2b) {
        final Map<String, Object> data = new HashMap<>();
        data.put("user[email]", email);
        data.put("user[first_name]", firstName);
        data.put("user[last_name]", lastName);
        data.put("user[password]", password);
        if (location != null && !location.isEmpty()) data.put("user[location]", location);
        data.put("user[privacy_terms]", terms);
        data.put("user[promo_terms_accepted]", promoTerms);
        if (anonymousId > 0) data.put("user[anonymous_id]", anonymousId);
        data.put("user[b2b]", b2b);

        return givenWithSpec()
                .formParams(data)
                .post(ApiV2EndPoints.USERS);
    }
}
