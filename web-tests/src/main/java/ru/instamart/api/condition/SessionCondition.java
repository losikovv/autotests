package instamart.api.condition;

import instamart.api.SessionFactory;
import instamart.api.action.Authorization;
import instamart.api.enums.v2.AuthProvider;
import instamart.api.validation.SessionsCheck;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;

public final class SessionCondition implements ICondition<SessionCondition> {

    private final SessionsCheck sessionsCheck;
    private Response response;

    private SessionCondition() {
        this.sessionsCheck = SessionsCheck.newCheck(this);
    }

    public SessionsCheck externalAuth(final AuthProvider authProvider) {
        response = Authorization.externalAuth(authProvider);
        return sessionsCheck;
    }

    public SessionsCheck authWithClientId(final String clientId) {
        final UserData userData = UserManager.getUser();
        registration(userData);
        response = Authorization.authWithClientId(userData.getLogin(), userData.getPassword(), clientId);
        return sessionsCheck;
    }

    public SessionsCheck authWithInvalidData(final String login, final String password) {
        response = Authorization.auth(login, password);
        return sessionsCheck;
    }

    public SessionsCheck authWithValidData(final String login, final String password) {
        response = Authorization.auth(login, password);
        return sessionsCheck;
    }

    public SessionsCheck callSessionValidation(final String token) {
        response = Authorization.validateSessionToken(token);
        return sessionsCheck;
    }

    public SessionsCheck getUserData(final String token) {
        response = Authorization.getUserDataBYToken(token);
        return sessionsCheck;
    }

    public Response getResponse() {
        return response;
    }

    public static SessionCondition newTest() {
        return new SessionCondition();
    }
}
