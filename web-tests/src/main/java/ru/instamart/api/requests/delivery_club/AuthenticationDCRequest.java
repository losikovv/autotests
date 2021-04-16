package ru.instamart.api.requests.delivery_club;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.DeliveryClubEndpoints;
import ru.instamart.api.requests.DeliveryClubRequestBase;
import ru.instamart.ui.common.pagesdata.UserData;

public class AuthenticationDCRequest extends DeliveryClubRequestBase {

    public static class Token {

        @Step("{method} /" + DeliveryClubEndpoints.Authentication.TOKEN)
        public static Response POST(final String username, final String password) {
            return givenWithSpec()
                    .auth().preemptive().basic(username,password)
                    .post(DeliveryClubEndpoints.Authentication.TOKEN);
        }

        public static Response POST(final UserData userData) {
            return POST(userData.getLogin(), userData.getPassword());
        }
    }
}
