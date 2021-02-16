package instamart.api.action;

import instamart.api.requests.ApiV2Requests;
import io.restassured.response.Response;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

public final class Authorization {

    public static Response authorisation(final String email, final String password) {
        final Response response = ApiV2Requests.Sessions.POST(email, password);
        assertStatusCode200(response);
        return response;
    }
}
