package instamart.api.validation;

import instamart.api.condition.SessionCondition;
import instamart.api.responses.v2.SessionsResponse;
import instamart.api.responses.v2.UserDataResponse;
import instamart.ui.common.pagesdata.UserData;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public final class SessionsCheck {

    private final SessionCondition sessionCondition;

    private SessionsCheck(final SessionCondition sessionCondition) {
        this.sessionCondition = sessionCondition;
    }

    public SessionCondition checkExternalAuth() {
        assertStatusCode200(sessionCondition.getResponse());
        assertNotNull(sessionCondition.getResponse().as(SessionsResponse.class).getSession());
        return sessionCondition;
    }

    public SessionCondition checkAuthWithClientId() {
        assertStatusCode200(sessionCondition.getResponse(), "Не работает авторизация с Client-Id: InstamartApp");
        assertNotNull(sessionCondition.getResponse().as(SessionsResponse.class).getSession());
        return sessionCondition;
    }

    public SessionCondition checkInvalidAuth() {
        assertStatusCode401(sessionCondition.getResponse());
        return sessionCondition;
    }

    public SessionCondition checkValidAuth() {
        assertStatusCode200(sessionCondition.getResponse());
        final SessionsResponse sessionResponse = sessionCondition.getResponse().as(SessionsResponse.class);
        assertNotNull(sessionResponse.getSession().getAccess_token());
        return sessionCondition;
    }

    public SessionCondition checkSessionToken() {
        assertStatusCode200(sessionCondition.getResponse());
        return sessionCondition;
    }

    public SessionCondition checkInvalidSessionToken() {
        assertStatusCode404(sessionCondition.getResponse());
        return sessionCondition;
    }

    public SessionCondition checkUserData(final UserData userData) {
        assertStatusCode200(sessionCondition.getResponse());
        final UserDataResponse userDataResponse = sessionCondition.getResponse().as(UserDataResponse.class);
        assertEquals(userDataResponse.getUser().getEmail(), userData.getLogin(), "Получены чужие данные");
        return sessionCondition;
    }

    public SessionCondition checkUserDataWithInvalidToken() {
        assertStatusCode404(sessionCondition.getResponse());
        return sessionCondition;
    }

    public static SessionsCheck newCheck(final SessionCondition sessionCondition) {
        return new SessionsCheck(sessionCondition);
    }
}
