package instamart.api;

import com.google.common.base.Objects;
import instamart.api.checkpoints.ShopperApiCheckpoints;
import instamart.api.enums.SessionType;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.objects.shopper.SessionAttributes;
import instamart.api.requests.delivery_club.AuthenticationDCRequest;
import instamart.api.requests.shopper.SessionsRequest;
import instamart.api.requests.v2.SessionRequest;
import instamart.api.responses.deliveryclub.TokenDCResponse;
import instamart.api.responses.v2.SessionsResponse;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public final class SessionFactory {

    private static final Logger log = LoggerFactory.getLogger(SessionFactory.class);
    private static final Map<SessionId, SessionInfo> sessionMap = new ConcurrentHashMap<>();

    public static void makeSession(final SessionType type) {
        switch (type) {
            case APIV1:
            case SHOPPER:
            case DELIVERY_CLUB:
                break;
            case APIV2:
                    final UserData userData = UserManager.getUser();
                    RegistrationHelper.registration(userData);
                    createSessionToken(type, userData);
                break;
            default:
                log.error("Pls select session type");
                break;
        }
    }

    public static SessionInfo getSession(final SessionType type) {
        for (Map.Entry<SessionId, SessionInfo> entry : sessionMap.entrySet()) {
            final SessionId sessionId = entry.getKey();
            if (sessionId.getThreadId() == Thread.currentThread().getId() && sessionId.getType() == type) {
                log.debug("Get Session {} for {}", entry.getValue(), type);
                Assert.assertNotNull(entry.getValue(),"Вы не авторизовались");

                return entry.getValue();
            }
        }
        log.error("Get default session");
        return new SessionInfo();
    }

    public static void createSessionToken(final SessionType type, final UserData userData) {
        final SessionId sessionId = new SessionId(Thread.currentThread().getId(), type);
        final SessionInfo session = sessionMap.get(sessionId);
        if (session != null && !session.getLogin().equals(userData.getLogin())) {
            sessionMap.put(sessionId, createSession(type, userData));
        } else if (session == null) {
            sessionMap.put(sessionId, createSession(type, userData));
        }
    }

    public static Map<SessionId, SessionInfo> getAllSession() {
        return sessionMap;
    }

    private static SessionInfo createSession(final SessionType type, final UserData userData) {
        SessionInfo sessionInfo;
        switch (type) {
            case APIV1:
                sessionInfo = createApiV1Session(userData);
                log.info("Session created {}", sessionInfo);
                return sessionInfo;
            case APIV2:
                sessionInfo = createApiV2Session(userData);
                log.info("Session created {}", sessionInfo);
                return sessionInfo;
            case SHOPPER:
                sessionInfo = createShopperSession(userData);
                log.info("Session created {}", sessionInfo);
                return sessionInfo;
            case DELIVERY_CLUB:
                sessionInfo = createDeliveryClubSession(userData);
                log.info("Session created {}", sessionInfo);
                return sessionInfo;
            default:
                log.error("Session type not selected");
                return new SessionInfo();
        }
    }

    //TODO: Wait ApiV1 refactoring
    private static SessionInfo createApiV1Session(final UserData userData) {
        return new SessionInfo();
    }

    private static SessionInfo createApiV2Session(final UserData userData) {
        final Response response = SessionRequest.POST(userData.getLogin(), userData.getPassword());
        checkStatusCode200(response);
        final SessionsResponse sessionResponse = response.as(SessionsResponse.class);
        log.info("Авторизуемся: {} / {}", userData.getLogin(), userData.getPassword());
        log.info("access_token: {}", sessionResponse.getSession().getAccessToken());
        return new SessionInfo(userData, sessionResponse.getSession().getAccessToken());
    }

    private static SessionInfo createShopperSession(final UserData userData) {
        final Response response = SessionsRequest.POST(userData.getLogin(), userData.getPassword());
        ShopperApiCheckpoints.checkStatusCode200(response);
        final instamart.api.responses.shopper.SessionsResponse sessionsResponse = response.as(instamart.api.responses.shopper.SessionsResponse.class);
        final SessionAttributes sessionAttributes = sessionsResponse.getData().getAttributes();
        log.info("Авторизуемся: {} / {}", userData.getLogin(), userData.getPassword());
        log.info("access_token: {}", sessionAttributes.getAccessToken());
        log.info("refresh_token: {}", sessionAttributes.getRefreshToken());
        return new SessionInfo(userData, sessionAttributes.getAccessToken(), sessionAttributes.getRefreshToken());
    }

    private static SessionInfo createDeliveryClubSession(final UserData userData) {
        final Response response = AuthenticationDCRequest.Token.POST(userData);
        checkStatusCode200(response);
        final TokenDCResponse sessionResponse = response.as(TokenDCResponse.class);
        log.info("Авторизуемся: {} / {}", userData.getLogin(), userData.getPassword());
        log.info("token: {}", sessionResponse.getToken());
        return new SessionInfo(userData, sessionResponse.getToken());
    }

    private static final class SessionId {
        private final long threadId;
        private final SessionType type;

        public SessionId(final long threadId, final SessionType type) {
            this.threadId = threadId;
            this.type = type;
        }

        public long getThreadId() {
            return threadId;
        }

        public SessionType getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final SessionId sessionId = (SessionId) o;
            return threadId == sessionId.threadId && type == sessionId.type;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(threadId, type);
        }

        @Override
        public String toString() {
            return "SessionId{" +
                    "threadId=" + threadId +
                    ", type=" + type +
                    '}';
        }
    }

    public static final class SessionInfo {

        private final UserData userData;
        private String token;
        private String refreshToken;

        public SessionInfo() {
            this(UserManager.getNullUser(), "invalid", "invalid_refresh");
        }

        public SessionInfo(final UserData userData, final String token) {
            this(userData, token, "empty");
        }

        public SessionInfo(final UserData userData, final String token, final String refreshToken) {
            this.userData = userData;
            this.token = token;
            this.refreshToken = refreshToken;
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

        public void setToken(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setRefreshToken(final String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final SessionInfo that = (SessionInfo) o;
            return Objects.equal(userData, that.userData) && Objects.equal(token, that.token) && Objects.equal(refreshToken, that.refreshToken);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(userData, token, refreshToken);
        }

        @Override
        public String toString() {
            return "SessionInfo{" +
                    "userData=" + userData +
                    ", token='" + token + '\'' +
                    ", refreshToken='" + refreshToken + '\'' +
                    '}';
        }
    }
}
