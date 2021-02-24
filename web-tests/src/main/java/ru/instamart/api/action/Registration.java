package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import instamart.api.responses.v2.UserResponse;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Registration {

    private static final Logger log = LoggerFactory.getLogger(Registration.class);

    private Registration() {}

    public static void registration(final UserData user) {
        String[] fullName = new String[0];
        String firstName = null;
        String lastName = null;
        if (user.getName() != null) fullName = user.getName().split(" ",2);
        if (fullName.length > 0) firstName = fullName[0];
        if (fullName.length > 1) lastName = fullName[1];

        registration(
                user.getLogin(),
                firstName,
                lastName,
                user.getPassword());
    }

    /**
     * Регистрация
     */
    public static void registration(final String email, final String firstName, final String lastName, final String password) {
        final Response response =  POST(email, firstName, lastName, password);
        assertStatusCode200(response);
        final String registeredEmail = response
                .as(UserResponse.class)
                .getUser()
                .getEmail();
        log.info("Зарегистрирован: {}", registeredEmail);
    }

    @Step("{method} /" + ApiV2EndPoints.Users.USERS)
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

        return givenCatch()
                .formParams(data)
                .post(ApiV2EndPoints.Users.USERS);
    }
}
