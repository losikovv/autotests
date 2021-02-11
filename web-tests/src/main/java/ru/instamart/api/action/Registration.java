package instamart.api.action;

import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.UserResponse;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

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
        final Response response =  ApiV2Requests.Users.POST(email, firstName, lastName, password);
        assertStatusCode200(response);
        final String registeredEmail = response
                .as(UserResponse.class)
                .getUser()
                .getEmail();
        log.info("Зарегистрирован: {}", registeredEmail);
    }
}
