package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Users {

    private Users() {}

    @Step("{method} /" + ApiV2EndPoints.Users.USERS_EMAIL)
    public static Response GET(final String email) {
        return givenCatch()
                .get(ApiV2EndPoints.Users.USERS_EMAIL, email);
    }

    @Step("{method} /" + ApiV2EndPoints.Users.USERS_EMAIL)
    public static Response GET(final String token, final String email) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .get(ApiV2EndPoints.Users.USERS_EMAIL, email);
    }

    @Step("{method} /" + ApiV2EndPoints.Users.USERS_EMAIL)
    public static Response PUT(final String token,
                               final String email,
                               final String firstName,
                               final String lastName,
                               final boolean promo) {
        Assert.assertNotNull(token , "Token is NULL");
        Assert.assertNotEquals(token , "", "Token is empty");
        Assert.assertNotNull(email , "Email is NULL");
        Assert.assertNotEquals(email , "", "Email is empty");

        final Map<String, Object> data = new HashMap<>();
        if (firstName != null && !firstName.isEmpty()) data.put("user[first_name]", firstName);
        if (lastName != null && !lastName.isEmpty()) data.put("user[last_name]", lastName);
        data.put("user[promo_terms_accepted]", promo);
        return givenCatch()
                .header("Authorization",
                "Token token=" + token)
                .formParams(data)
                .put(ApiV2EndPoints.Users.USERS_EMAIL, email);
    }
}
