package ru.instamart.api.requests.delivery_club;

import ru.instamart.api.endpoints.DeliveryClubEndpoints;
import ru.instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public class AuthenticationDCRequest {

    public static class Token {

        @Step("{method} /" + DeliveryClubEndpoints.Authentication.TOKEN)
        public static Response POST(final String username, final String password) {
            return givenCatch()
                    .auth().preemptive().basic(username,password)
                    .post(DeliveryClubEndpoints.Authentication.TOKEN);
        }

        public static Response POST(final UserData userData) {
            return POST(userData.getLogin(), userData.getPassword());
        }
    }
}
