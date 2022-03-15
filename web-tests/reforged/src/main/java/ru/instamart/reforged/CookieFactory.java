package ru.instamart.reforged;

import org.openqa.selenium.Cookie;
import ru.instamart.kraken.data.user.UserManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class CookieFactory {

    private static final Date date = new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime();
    private static final String ANONYMOUS_ID = UserManager.getGuestQaWithoutAb();
    private static final String FIXED_ANONYMOUS_ID = "49f6c82a-b00d-42f5-ba0a-3c2a1875194c";

    //Алерт о политике хранения cookies
    public static final Cookie COOKIE_ALERT = new Cookie("cookies_consented",
            "yes",
            "sbermarket.tech",
            "/",
            null);

    //Окно выбора магазина
    public static final Cookie RETAILERS_REMINDER_MODAL = new Cookie("isRetailersModalReminderShown",
            "1",
            "sbermarket.tech",
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID = new Cookie("external_analytics_anonymous_id",
            ANONYMOUS_ID,
            "sbermarket.tech",
            "/",
            date);

    public static final Cookie EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE = new Cookie("external_analytics_anonymous_id",
            FIXED_ANONYMOUS_ID,
            "sbermarket.tech",
            "/",
            date);

    public static Cookie setLocation(final String cityName) {
        return new Cookie("city_info",
                "%7B%22slug%22%3A%22" + cityName + "%22%2C%22lat%22%3A00.0000%2C%22lon%22%3A00.0000%7D",
                "sbermarket.tech",
                "/",
                date);
    }
}
