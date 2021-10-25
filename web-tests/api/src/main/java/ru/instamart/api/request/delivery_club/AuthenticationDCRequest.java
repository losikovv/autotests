package ru.instamart.api.request.delivery_club;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.DeliveryClubEndpoints;
import ru.instamart.api.request.DeliveryClubRequestBase;
import ru.instamart.kraken.data.user.UserData;

public class AuthenticationDCRequest extends DeliveryClubRequestBase {

    public static class Token {

        @Step("{method} /" + DeliveryClubEndpoints.Authentication.TOKEN)
        public static Response POST(final String username, final String password) {
            return givenWithSpec()
                    .auth().preemptive().basic(username,password)
                    .post(DeliveryClubEndpoints.Authentication.TOKEN);
        }

        public static Response POST(final UserData userData) {
            return POST(userData.getEmail(), userData.getPassword());
        }
    }
}
