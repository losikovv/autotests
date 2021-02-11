package instamart.api.action;

import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.SessionsResponse;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

public final class Authorization {

    private static final Logger log = LoggerFactory.getLogger(Authorization.class);

    public void authorisation(final String email, final String password) {
        final Response response = ApiV2Requests.Sessions.POST(email, password);
        assertStatusCode200(response);
        ApiV2Requests.setToken(response
                .as(SessionsResponse.class)
                .getSession()
                .getAccess_token());
        log.info("Авторизуемся: {} / {}", email, password);
        log.info("access_token: {}", ApiV2Requests.getToken());
    }
}
