package ru.instamart.api.helpers;

import ru.instamart.api.requests.v2.UsersRequest;
import ru.instamart.api.responses.v2.UserResponse;
import ru.instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

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
        final Response response =  UsersRequest.POST(email, firstName, lastName, password);
        checkStatusCode200(response);
        final String registeredEmail = response
                .as(UserResponse.class)
                .getUser()
                .getEmail();
        log.info("Зарегистрирован: {}", registeredEmail);
    }
}
