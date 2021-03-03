package instamart.api;

import instamart.api.action.Registration;
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
        Registration.registration(userData);
        createSessionToken(userData.getLogin(), userData.getPassword());
    }

    public static SessionInfo getSession() {
        final SessionInfo session = sessionMap.get(Thread.currentThread().getId());
        Assert.assertNotNull(session,"Вы не авторизовались");

        return session;
    }

    public static void createSessionToken(final String login, final String password) {
        final SessionInfo session = sessionMap.get(Thread.currentThread().getId());
        if (session != null && !session.getLogin().equals(login)) {
            sessionMap.put(Thread.currentThread().getId(), createSession(login, password));
        } else if (session == null) {
            sessionMap.put(Thread.currentThread().getId(), createSession(login, password));
        }
    }

    public static Map<Long, SessionInfo> getAllSession() {
        return sessionMap;
    }

    private static SessionInfo createSession(final String login, final String password) {
        final Response response = instamart.api.action.Session.POST(login, password);
        assertStatusCode200(response);
        final SessionsResponse sessionResponse = response.as(SessionsResponse.class);
        return new SessionInfo(new UserData(login, password), sessionResponse.getSession().getAccess_token());
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
