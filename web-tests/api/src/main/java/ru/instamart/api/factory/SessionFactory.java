package ru.instamart.api.factory;

import io.restassured.response.Response;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.shopper.app.SessionSHP;
import ru.instamart.api.model.v1.ShoppersBackendV1;
import ru.instamart.api.request.delivery_club.AuthenticationDCRequest;
import ru.instamart.api.request.ris_exporter.AuthenticationRisRequest;
import ru.instamart.api.request.shopper.app.AuthSHPRequest;
import ru.instamart.api.request.shopper.app.OtpCodeSHPRequest;
import ru.instamart.api.request.v1.PhoneConfirmationsV1Request;
import ru.instamart.api.request.v1.TokensV1Request;
import ru.instamart.api.request.v1.UserSessionsV1Request;
import ru.instamart.api.request.v2.AuthProvidersV2Request;
import ru.instamart.api.request.v2.PhoneConfirmationsV2Request;
import ru.instamart.api.response.delivery_club.TokenDCResponse;
import ru.instamart.api.response.ris_exporter.TokenRisResponse;
import ru.instamart.api.response.shopper.app.OtpCodeSHPResponse;
import ru.instamart.api.response.shopper.app.SessionsSHPResponse;
import ru.instamart.api.response.v1.PhoneConfirmationsV1Response;
import ru.instamart.api.response.v1.TokensV1Response;
import ru.instamart.api.response.v2.SessionsV2Response;
import ru.instamart.api.response.v2.UserV2Response;
import ru.instamart.jdbc.dao.stf.PhoneTokensDao;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200or422;

@Slf4j
public final class SessionFactory {

    @Setter
    private static SessionProvider provider = SessionProvider.QA;

    @Getter
    private static final Map<SessionId, SessionInfo> sessionMap = new ConcurrentHashMap<>();

    public static void makeSession(final SessionType type) {
        makeSession(type, provider);
    }

    public static void makeSession(final SessionType type, final SessionProvider provider) {
        final UserData userData = provider.equals(SessionProvider.QA) ? UserManager.getQaUser() : UserManager.getUser();
        switch (type) {
            case API_V1:
            case SHOPPER_APP:
            case SHOPPER_ADMIN:
            case DELIVERY_CLUB:
            case RIS_EXPORTER:
                log.warn("Not implemented yet!");
                break;
            case API_V2:
                if (!provider.equals(SessionProvider.PHONE) && !provider.equals(SessionProvider.QA)) {
                    RegistrationHelper.registration(userData);
                }
                createSessionToken(type, provider, userData);
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

    public static void getAllSessionUpdateToken(final SessionType type, final String accessToken, final String refreshToken) {
        sessionMap.entrySet().stream()
//                .filter(entry -> entry.getKey().getType().equals(type))
                .filter(token -> token.getValue().getToken().equals(SessionFactory.getSession(type).getToken()))
                .forEach(item -> {
                    SessionInfo sessionInfo = item.getValue();
                    sessionInfo.setToken(accessToken);
                    sessionInfo.setRefreshToken(refreshToken);
                    item.setValue(sessionInfo);
                });
    }

    /**
     * Создание сессии без провайдера для
     * {@link  ru.instamart.api.enums.SessionType#API_V1 SessionType.API_V1}
     * {@link  ru.instamart.api.enums.SessionType#SHOPPER_APP SessionType.SHOPPER_APP}
     * {@link  ru.instamart.api.enums.SessionType#SHOPPER_ADMIN SessionType.SHOPPER_ADMIN}
     * {@link  ru.instamart.api.enums.SessionType#DELIVERY_CLUB SessionType.DELIVERY_CLUB}
     * {@link  ru.instamart.api.enums.SessionType#ADMIN SessionType.ADMIN}
     *
     * @param type     see {@link ru.instamart.api.enums.SessionType SessionType}
     * @param userData get default user {@link ru.instamart.kraken.data.user.UserManager#getDefaultUser UserManager.getDefaultUser}
     */
    public static void createSessionToken(final SessionType type, final UserData userData) {
        createSessionToken(type, provider, userData);
    }

    /**
     * Создание сессии с провайдером авторизации для {@link  ru.instamart.api.enums.SessionType#API_V2 SessionType.API_V2}
     *
     * @param type     see {@link ru.instamart.api.enums.SessionType SessionType}
     * @param provider see {@link ru.instamart.api.enums.SessionProvider SessionProvider}
     * @param userData get default user {@link ru.instamart.kraken.data.user.UserManager#getDefaultUser UserManager.getDefaultUser}
     */
    public static void createSessionToken(final SessionType type, final SessionProvider provider, final UserData userData) {
        final SessionId sessionId = new SessionId(Thread.currentThread().getId(), type);
        final SessionInfo session = sessionMap.get(sessionId);
        if (nonNull(session) && !session.getLogin().equals(userData.getEmail())) {
            //TODO  исправить проверку номера телефона
            // if (nonNull(session) && (!session.getLogin().equals(userData.getEmail()) || !session.getPhone().equals(userData.getPhone()))) {
            addSessionMap(type, provider, userData);
        } else if (isNull(session)) {
            addSessionMap(type, provider, userData);
        }

    }

    private static void addSessionMap(final SessionType type, final SessionProvider provider, final UserData userData) {
        final SessionId sessionId = new SessionId(Thread.currentThread().getId(), type);
        if (nonNull(EnvironmentProperties.Env.ONE_SESSION)) {
            ThreadUtil.simplyAwait(Math.random() * 10);
            Map<SessionId, SessionInfo> collect = sessionMap.entrySet().stream()
                    .filter(item -> item.getKey().getType().equals(type))
                    .filter(item ->
                            (item.getValue().getLogin().equals(userData.getEmail()) || item.getValue().getPhone().equals(userData.getPhone()))
                    )
                    .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

            if (!collect.isEmpty()) {
                log.debug("Используем существующую сессию для пользователя {}, тип сессии {}, поток {}", userData.getPhone(), type, sessionId.getThreadId());
                sessionMap.put(sessionId, collect.entrySet().iterator().next().getValue());
            } else {
                log.debug("Используем новую сессию для пользователя {}, тип сессии {}, поток {}", userData.getPhone(), type, sessionId.getThreadId());
                sessionMap.put(sessionId, createSession(type, provider, userData));
            }
        } else {
            sessionMap.put(sessionId, createSession(type, provider, userData));
        }
    }

    private static SessionInfo createSession(final SessionType type, final SessionProvider provider, final UserData userData) {
        switch (type) {
            case API_V1:
                return createApiV1Session(provider, userData);
            case API_V2:
                return createSession(provider, userData);
            case SHOPPER_APP:
                return createShopperAppSession(userData);
            case SHOPPER_ADMIN:
                return createShopperAdminSession(userData);
            case DELIVERY_CLUB:
                return createDeliveryClubSession(userData);
            case ADMIN:
                return createAdminSession(userData);
            case RIS_EXPORTER:
                return createRisSession(userData);
            default:
                log.error("Session type not selected");
                return new SessionInfo();
        }
    }

    private static SessionInfo createSession(final SessionProvider provider, final UserData userData) {
        switch (provider) {
            case VK:
            case MAILRU:
            case SBER_ID:
                log.error("Not implemented yet!");
                return new SessionInfo();
            case FB:
                return createApiV2FacebookSession(userData);
            case PHONE:
                return createApiV2PhoneSession(userData);
            case QA:
                return createQaSession(userData);
            default:
                log.error("Session type not selected");
                return new SessionInfo();
        }
    }

    private static SessionInfo createApiV1Session(final SessionProvider provider, final UserData userData) {
        switch (provider) {
            case EMAIL:
                final Response response = UserSessionsV1Request.POST(userData.getEmail(), userData.getPassword());
                checkStatusCode200(response);
                log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
                log.debug("cookies: {}", response.getCookies());
                UserV2Response userV2Response = response.as(UserV2Response.class);
                userData.setId(userV2Response.getUser().getId());
                return new SessionInfo(userData, userV2Response.getCsrfToken(), response.getCookies());
            case PHONE:
                final Response postResponse = PhoneConfirmationsV1Request.POST(userData.getEncryptedPhone());
                checkStatusCode200(postResponse);
                String phone = userData.getPhone();
                final Response phoneResponse = PhoneConfirmationsV1Request.PUT(phone, PhoneTokensDao.INSTANCE.getByPhoneValue(phone).getConfirmationCode());
                checkStatusCode200(phoneResponse);
                log.debug("Авторизуемся: {}", userData.getPhone());
                log.debug("cookies: {}", phoneResponse.getCookies());
                return new SessionInfo(userData, phoneResponse.as(PhoneConfirmationsV1Response.class).getCsrfToken(), phoneResponse.getCookies());
            default:
                log.error("Session type not selected");
                return new SessionInfo();
        }
    }

    private static SessionInfo createApiV2PhoneSession(final UserData userData) {
        final var postResponse = PhoneConfirmationsV2Request.POST(userData.getEncryptedPhone());
        checkStatusCode200(postResponse);
        ThreadUtil.simplyAwait(1);
        var response = PhoneConfirmationsV2Request.PUT(userData.getPhone(), userData.getSmsCode(), true);
        checkStatusCode200(response);
        final var sessionResponse = response.as(SessionsV2Response.class);
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
        String prodOtp = null;
        final Response sendCodeResponse = AuthSHPRequest.Login.POST(userData.getPhone());
        checkStatusCode200or422(sendCodeResponse); // 422 ошибка - если отп-код еще не протух
        if (EnvironmentProperties.Env.isProduction()) {
            final Response otpCodeResponse = OtpCodeSHPRequest.GET(userData.getPhone());
            checkStatusCode200(otpCodeResponse);
            prodOtp = otpCodeResponse.as(OtpCodeSHPResponse.class).getCode();
            checkFieldIsNotEmpty(prodOtp, "OTP-код");
        }
        final Response response = AuthSHPRequest.Code.POST(userData.getPhone(), EnvironmentProperties.Env.isProduction() ? prodOtp : CoreProperties.DEFAULT_SMS);
        checkStatusCode200(response);
        final SessionSHP.Data.Attributes sessionAttributes = response.as(SessionsSHPResponse.class).getData().getAttributes();
        log.debug("Авторизуемся: {}", userData.getPhone());
        log.debug("access_token: {}", sessionAttributes.getAccessToken());
        log.debug("refresh_token: {}", sessionAttributes.getRefreshToken());
        return new SessionInfo(userData, sessionAttributes.getAccessToken(), sessionAttributes.getRefreshToken());
    }

    private static SessionInfo createShopperAdminSession(final UserData userData) {
        createSessionToken(SessionType.API_V1, SessionProvider.EMAIL, UserManager.getDefaultAdmin());
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

    private static SessionInfo createAdminSession(final UserData userData) {
        final Response response = UserSessionsV1Request.POST(userData.getEmail(), userData.getPassword());
        checkStatusCode200(response);
        log.debug("Авторизуемся: {} / {}", userData.getEmail(), userData.getPassword());
        log.debug("cookies: {}", response.getCookies());
        return new SessionInfo(userData, response.as(UserV2Response.class).getCsrfToken(), response.getCookies());
    }

    private static SessionInfo createRisSession(final UserData userData) {
        final Response response = AuthenticationRisRequest.POST(userData.getToken());
        checkStatusCode200(response);
        log.debug("Авторизуемся с токеном: {}", userData.getToken());
        return new SessionInfo(userData, response.as(TokenRisResponse.class).getToken());
    }

    private static SessionInfo createQaSession(final UserData userData) {
        return new SessionInfo(userData, userData.getToken());
    }

    public static void updateToken(final SessionType sessionType, final String accessToken, final String refreshToken) {
        if (nonNull(EnvironmentProperties.Env.ONE_SESSION)) {
            getAllSessionUpdateToken(sessionType, accessToken, refreshToken);
        } else {
            SessionFactory.getSession(sessionType).setToken(accessToken);
            SessionFactory.getSession(sessionType).setRefreshToken(refreshToken);
        }
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

        public SessionInfo(final UserData userData, final String token, final Map<String, String> cookies) {
            this(userData, token, "empty", cookies);
        }

        public SessionInfo(final UserData userData, final String token, final String refreshToken) {
            this(userData, token, refreshToken, new HashMap<>());
        }

        public String getPhone() {
            return this.userData.getPhone();
        }

        public String getLogin() {
            return this.userData.getEmail();
        }

        public String getPassword() {
            return this.userData.getPassword();
        }
    }
}
