package instamart.api;

import instamart.api.helpers.RegistrationHelper;
import instamart.api.responses.v2.SessionsResponse;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

public final class SessionFactory {

    private static final Map<Long, SessionInfo> sessionMap = new ConcurrentHashMap<>();

    public static void makeSession() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        createSessionToken(userData);
    }

    public static SessionInfo getSession() {
        final SessionInfo session = sessionMap.get(Thread.currentThread().getId());
        Assert.assertNotNull(session,"Вы не авторизовались");

        return session;
    }

    public static void createSessionToken(final UserData userData) {
        final SessionInfo session = sessionMap.get(Thread.currentThread().getId());
        if (session != null && !session.getLogin().equals(userData.getLogin())) {
            sessionMap.put(Thread.currentThread().getId(), createSession(userData));
        } else if (session == null) {
            sessionMap.put(Thread.currentThread().getId(), createSession(userData));
        }
    }

    public static Map<Long, SessionInfo> getAllSession() {
        return sessionMap;
    }

    private static SessionInfo createSession(final UserData userData) {
        final Response response = instamart.api.action.Session.POST(userData.getLogin(), userData.getPassword());
        assertStatusCode200(response);
        final SessionsResponse sessionResponse = response.as(SessionsResponse.class);
        return new SessionInfo(userData, sessionResponse.getSession().getAccess_token());
    }

    public static final class SessionInfo {

        private final UserData userData;
        private final String token;

        public SessionInfo(final UserData userData, final String token) {
            this.userData = userData;
            this.token = token;
        }

        public UserData getUserData() {
            return  this.userData;
        }

        public String getLogin() {
            return this.userData.getLogin();
        }

        public String getPassword() {
            return this.userData.getPassword();
        }

        public String getToken() {
            return token;
        }

        @Override
        public String toString() {
            return "SessionInfo{" +
                    "userData=" + userData +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
