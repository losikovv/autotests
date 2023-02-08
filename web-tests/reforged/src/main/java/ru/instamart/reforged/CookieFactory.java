package ru.instamart.reforged;

import org.openqa.selenium.Cookie;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class CookieFactory {

    private static final Date date = new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime();
    private static final String ANONYMOUS_ID = UserManager.getGuestQaWithoutAb();
    private static final String FIXED_ANONYMOUS_ID = "49f6c82a-b00d-42f5-ba0a-3c2a1875194c";
    private static final String FIXED_ANONYMOUS_ID_CHECKOUT = "ace4b8b6-21d7-4f41-9d7f-3f79eb75ee28";
    private static final String FIXED_ANONYMOUS_ID_NEW_CART = "8b9d55a4-4e52-4de0-b1dd-a802282c0c95";
    private static final String FIXED_CUSTOM_ANONYMOUS_ID = System.getenv("USER_UUID") != null ? System.getenv("USER_UUID") : ANONYMOUS_ID;
    private static final String COOKIE_DOMAIN = EnvironmentProperties.Env.isProduction() ? "sbermarket.ru" : ".sbermarket.tech";
    private static final String FORWARD_COOKIE_NAME = "sbm-forward-feature-version-stf";

    //Алерт о политике хранения cookies
    public static final Cookie COOKIE_ALERT = new Cookie("cookies_consented",
            "yes",
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST = new Cookie("external_analytics_anonymous_id",
            ANONYMOUS_ID,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE = new Cookie("external_analytics_anonymous_id",
            FIXED_ANONYMOUS_ID,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_CHECKOUT = new Cookie("external_analytics_anonymous_id",
            FIXED_ANONYMOUS_ID_CHECKOUT,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_CUSTOM_OR_GUEST = new Cookie("external_analytics_anonymous_id",
            FIXED_CUSTOM_ANONYMOUS_ID,
            COOKIE_DOMAIN,
            "/",
            date);


    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_NEW_CART = new Cookie("external_analytics_anonymous_id",
            FIXED_ANONYMOUS_ID_NEW_CART,
            COOKIE_DOMAIN,
            "/",
            date);

    //user_is_adult true
    public static final Cookie USER_ADULT_18_PLUS_ALERT = new Cookie("user_is_adult",
            "true",
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie FORWARD_FEATURE_STF = new Cookie(FORWARD_COOKIE_NAME,
            UiProperties.HEADER_STF_FORWARD_TO,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie FORWARD_FEATURE_B2B = new Cookie(FORWARD_COOKIE_NAME,
            UiProperties.HEADER_B2B_FORWARD_TO,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie FORWARD_FEATURE_SELGROS = new Cookie(FORWARD_COOKIE_NAME,
            UiProperties.HEADER_SELGROS_FORWARD_TO,
            COOKIE_DOMAIN,
            "/",
            date);

    public static final Cookie FORWARD_FEATURE_ADMIN = new Cookie(FORWARD_COOKIE_NAME,
            UiProperties.HEADER_ADMIN_FORWARD_TO,
            COOKIE_DOMAIN,
            "/",
            date);

    public static Cookie setLocation(final String cityName) {
        return new Cookie("city_info",
                "%7B%22slug%22%3A%22" + cityName + "%22%2C%22name%22%3A%22%D0%91%D1%80%D1%8F%D0%BD%D1%81%D0%BA%22%2C%22lat%22%3A53.2859%2C%22lon%22%3A34.3691%7D",
                COOKIE_DOMAIN,
                "/",
                null);
    }

    public static Cookie setFixedExternalAnalyticsAnonymousId(final String UUID) {
        return new Cookie("external_analytics_anonymous_id",
                UUID,
                COOKIE_DOMAIN,
                "/",
                date);
    }

    public static Cookie authStf(final UserData userData) {
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.PHONE, userData);
        final var sessionInfo = SessionFactory.getSession(SessionType.API_V1);

        final var sessionCookieOptional = sessionInfo.getCookies()
                .asList()
                .stream()
                .filter(c -> c.getName().equals("_Instamart_session"))
                .findFirst();

        if (sessionCookieOptional.isPresent()) {
            final var sessionCookie = sessionCookieOptional.get();
            return new Cookie(sessionCookie.getName(), sessionCookie.getValue(), COOKIE_DOMAIN, "/", null);
        }

        return new Cookie("session", "invalid", COOKIE_DOMAIN, "/", date);
    }
}
