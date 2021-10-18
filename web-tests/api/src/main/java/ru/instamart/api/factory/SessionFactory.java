package ru.instamart.api.factory;

import io.restassured.response.Response;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.api.checkpoint.ShopperApiCheckpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.shopper.app.SessionSHP;
import ru.instamart.api.model.v1.ShoppersBackendV1;
import ru.instamart.api.request.delivery_club.AuthenticationDCRequest;
import ru.instamart.api.request.shopper.app.SessionsSHPRequest;
import ru.instamart.api.request.v1.TokensV1Request;
import ru.instamart.api.request.v1.UserSessionsV1Request;
import ru.instamart.api.request.v2.AuthProvidersV2Request;
import ru.instamart.api.request.v2.PhoneConfirmationsV2Request;
import ru.instamart.api.response.delivery_club.TokenDCResponse;
import ru.instamart.api.response.shopper.app.SessionsSHPResponse;
import ru.instamart.api.response.v1.TokensV1Response;
import ru.instamart.api.response.v2.SessionsV2Response;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
public final class SessionFactory {

    @Getter
    private static final Map<SessionId, SessionInfo> sessionMap = new ConcurrentHashMap<>();

    public static void makeSession(final SessionType type) {
        final UserData userData = UserManager.getUser();
        switch (type) {
            case API_V1:
            case SHOPPER_APP:
            case SHOPPER_ADMIN:
            case DELIVERY_CLUB:
                log.warn("Not implemented yet!");
                break;
            case API_V2_FB:
                RegistrationHelper.registration(userData);
            case API_V2_PHONE:
                createSessionToken(type, userData);
                break;
            default:
                log.error("Pls select session type");
                break;
        }
    }

    public static void clearSession(final SessionType type) {
        sessionMap.entrySet().removeIf(session -> {
            final SessionId sessionId = session.getKey();
            return sessionId.getThreadId() == Thread.currentThread().getId() && sessionId.getType() == type;
        });
        log.debug("Current {} session is cleared", type);
    }

    public static SessionInfo getSession(final SessionType type) {
        for (Map.Entry<SessionId, SessionInfo> entry : sessionMap.entrySet()) {
            final SessionId sessionId = entry.getKey();
            if (sessionId.getThreadId() == Thread.currentThread().getId() && sessionId.getType() == type) {
                log.debug("Get Session {} for {}", entry.getValue(), type);
                Assert.assertNotNull(entry.getValue(), "Вы не авторизовались");
                return entry.getValue();
            }
        }
        log.error("Get default session");
        return new SessionInfo();
    }

    public static void createSessionToken(final SessionType type, final UserData userData) {
        final SessionId sessionId = new SessionId(Thread.currentThread().getId(),
                type == SessionType.API_V2_PHONE ? SessionType.API_V2_FB : type);
        final SessionInfo session = sessionMap.get(sessionId);
        //TODO: В связи с тем, что теперь для логина используется телефон, нужно перейти с email во всех проверках создания сессии
        if (nonNull(session) && !session.getLogin().equals(userData.getEmail())) {
            sessionMap.put(sessionId, createSession(type, userData));
        } else if (isNull(session)) {
            sessionMap.put(sessionId, createSession(type, userData));
        }
    }

    private static SessionInfo createSession(final SessionType type, final UserData userData) {
        SessionInfo sessionInfo;
        switch (type) {
            case API_V1:
                sessionInfo = createApiV1Session(userData);
                return sessionInfo;
            case API_V2_FB:
                sessionInfo = createApiV2FacebookSession(userData);
                return sessionInfo;
            case API_V2_PHONE:
                sessionInfo = createApiV2PhoneSession(userData);
                return sessionInfo;
            case SHOPPER_APP:
                sessionInfo = createShopperAppSession(userData);
                return sessionInfo;
            case SHOPPER_ADMIN:
                sessionInfo = createShopperAdminSession(userData);
                return sessionInfo;
            case DELIVERY_CLUB:
                sessionInfo = createDeliveryClubSession(userData);
                return sessionInfo;
            default:
                log.error("Session type not selected");
                return new SessionInfo();
        }
    }

    private static SessionInfo createApiV1Session(final UserData userData) {
        final Response response = UserSessionsV1Request.POST(userData.getEmail(), userData.getPassword());
        checkStatusCode200(response);
        log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
        log.debug("cookies: {}", response.getCookies());
        return new SessionInfo(userData, response.getCookies());
    }

    private static SessionInfo createApiV2PhoneSession(final UserData userData) {
        final Response postResponse = PhoneConfirmationsV2Request.POST(userData.getEncryptedPhone());
        checkStatusCode200(postResponse);
        ThreadUtil.simplyAwait(1);
        final Response response = PhoneConfirmationsV2Request.PUT(userData.getPhone());
        checkStatusCode200(response);
        final SessionsV2Response sessionResponse = response.as(SessionsV2Response.class);
        log.debug("Авторизуемся: {}", userData.getPhone());
        log.debug("access_token: {}", sessionResponse.getSession().getAccessToken());
        return new SessionInfo(userData, sessionResponse.getSession().getAccessToken());
    }

    private static SessionInfo createApiV2FacebookSession(final UserData userData) {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK, userData, UUID.randomUUID().toString());
        checkStatusCode200(response);
        ThreadUtil.simplyAwait(1);
        final SessionsV2Response sessionResponse = response.as(SessionsV2Response.class);
        log.debug("Авторизуемся: {}", userData.getEmail());
        log.debug("access_token: {}", sessionResponse.getSession().getAccessToken());
        return new SessionInfo(userData, sessionResponse.getSession().getAccessToken());
    }

    private static SessionInfo createShopperAppSession(final UserData userData) {
        final Response response = SessionsSHPRequest.POST(userData.getEmail(), userData.getPassword());
        ShopperApiCheckpoints.checkStatusCode200(response);
        final SessionsSHPResponse sessionsResponse = response.as(SessionsSHPResponse.class);
        final SessionSHP.Data.Attributes sessionAttributes = sessionsResponse.getData().getAttributes();
        log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
        log.debug("access_token: {}", sessionAttributes.getAccessToken());
        log.debug("refresh_token: {}", sessionAttributes.getRefreshToken());
        return new SessionInfo(userData, sessionAttributes.getAccessToken(), sessionAttributes.getRefreshToken());
    }

    private static SessionInfo createShopperAdminSession(final UserData userData) {
        createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        final Response response = TokensV1Request.GET();
        checkStatusCode200(response);
        final ShoppersBackendV1 shoppersBackend = response.as(TokensV1Response.class).getShoppersBackend();
        final String token = "token=" + shoppersBackend.getClientJwt() + ", id=" + shoppersBackend.getClientId();
        log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
        log.debug("token: {}", token);
        return new SessionInfo(userData, token);
    }

    private static SessionInfo createDeliveryClubSession(final UserData userData) {
        final Response response = AuthenticationDCRequest.Token.POST(userData);
        checkStatusCode200(response);
        final TokenDCResponse sessionResponse = response.as(TokenDCResponse.class);
        log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
        log.debug("token: {}", sessionResponse.getToken());
        return new SessionInfo(userData, sessionResponse.getToken());
    }

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class SessionId {
        private final long threadId;
        private final SessionType type;
    }

    @AllArgsConstructor
    @Data
    public static final class SessionInfo {
        private final UserData userData;
        private String token;
        private String refreshToken;
        private Map<String, String> cookies;

        public SessionInfo() {
            this(UserManager.getNullUser(),
                    "invalid",
                    "invalid_refresh",
                    new HashMap<>());
        }

        public SessionInfo(final UserData userData, final String token) {
            this(userData, token, "empty", new HashMap<>());
        }

        public SessionInfo(final UserData userData, final Map<String, String> cookies) {
            this(userData, "empty", "empty", cookies);
        }

        public SessionInfo(final UserData userData, final String token, final String refreshToken) {
            this(userData, token, refreshToken, new HashMap<>());
        }

        public String getLogin() {
            return this.userData.getEmail();
        }

        public String getPassword() {
            return this.userData.getPassword();
        }
    }
}
