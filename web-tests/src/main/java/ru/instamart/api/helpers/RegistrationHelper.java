package instamart.api.helpers;

import instamart.api.action.Users;
import instamart.api.responses.v2.UserResponse;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

public final class RegistrationHelper {

    private static final Logger log = LoggerFactory.getLogger(RegistrationHelper.class);

    private RegistrationHelper() {}

    public static void registration(final UserData user) {
        registration(
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword());
    }

    /**
     * Регистрация
     */
    public static void registration(final String email, final String firstName, final String lastName, final String password) {
        final Response response =  Users.POST(email, firstName, lastName, password);
        assertStatusCode200(response);
        final String registeredEmail = response
                .as(UserResponse.class)
                .getUser()
                .getEmail();
        log.info("Зарегистрирован: {}", registeredEmail);
    }
}
