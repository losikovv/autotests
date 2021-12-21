package ru.instamart.reforged;

import org.openqa.selenium.Cookie;
import ru.instamart.kraken.data.user.UserManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class CookieFactory {

    private static final Date date = new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime();

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
            UserManager.getGuestQaWithoutAb(),
            "sbermarket.tech",
            "/",
            date);
}
