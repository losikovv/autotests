package instamart.api;

import instamart.api.action.Authorization;
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

    private static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

    public static void makeSession() {
        final UserData userData = UserManager.getUser();
        Registration.registration(userData);
        createSessionToken(userData.getLogin(), userData.getPassword());
    }

    public static Session getSession() {
        final Session session = sessionMap.get(Thread.currentThread().getId());
        Assert.assertNotNull(session,"Вы не авторизовались");

        return session;
    }

    public static void createSessionToken(final String login, final String password) {
        final Session session = sessionMap.get(Thread.currentThread().getId());
        if (session != null && !session.getLogin().equals(login)) {
            sessionMap.put(Thread.currentThread().getId(), createSession(login, password));
        } else if (session == null) {
            sessionMap.put(Thread.currentThread().getId(), createSession(login, password));
        }
    }

    public static Map<Long, Session> getAllSession() {
        return sessionMap;
    }

    private static Session createSession(final String login, final String password) {
        final Response response = Authorization.auth(login, password);
        assertStatusCode200(response);
        final SessionsResponse sessionResponse = response.as(SessionsResponse.class);
        return new Session(login, password, sessionResponse.getSession().getAccess_token());
    }

    public static final class Session {

        private final String login;
        private final String password;
        private final String token;

        public Session(final String login, final String password, final String token) {
            this.login = login;
            this.password = password;
            this.token = token;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getToken() {
            return token;
        }

        @Override
        public String toString() {
            return "Session{" +
                    "login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
