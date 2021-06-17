package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.request.v2.AuthProvidersV2Request;
import ru.instamart.kraken.testdata.UserData;

import java.util.UUID;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
public final class RegistrationHelper {

    private RegistrationHelper() {}

    /**
     * Регистрация
     */
    public static void registration(final UserData user) {
        final Response response = AuthProvidersV2Request.Sessions.POST(
                AuthProviderV2.FACEBOOK,
                user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPhone(),
                UUID.randomUUID().toString());
        checkStatusCode200(response);
//        final String registeredEmail = response
//                .as(UserV2Response.class)
//                .getUser()
//                .getEmail();
//        log.info("Зарегистрирован: {}", registeredEmail);
    }
}
