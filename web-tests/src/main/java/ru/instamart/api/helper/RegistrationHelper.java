package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.request.v2.UsersV2Request;
import ru.instamart.api.response.v2.UserV2Response;
import ru.instamart.ui.common.pagesdata.UserData;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
public final class RegistrationHelper {

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
        final Response response =  UsersV2Request.POST(email, firstName, lastName, password);
        checkStatusCode200(response);
        final String registeredEmail = response
                .as(UserV2Response.class)
                .getUser()
                .getEmail();
        log.info("Зарегистрирован: {}", registeredEmail);
    }
}
