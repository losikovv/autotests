package ru.instamart.reforged;

import org.openqa.selenium.Cookie;

public final class CookieFactory {

    public static final Cookie COOKIE_ALERT = new Cookie("cookies_consented",
            "yes",
            "sbermarket.tech",
            "/",
            null);
}
