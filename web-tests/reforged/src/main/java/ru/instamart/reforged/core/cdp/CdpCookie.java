package ru.instamart.reforged.core.cdp;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.CookiePriority;
import org.openqa.selenium.devtools.v102.network.model.CookieSourceScheme;
import org.openqa.selenium.devtools.v102.network.model.TimeSinceEpoch;
import ru.instamart.reforged.core.Kraken;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public final class CdpCookie {

    public static List<org.openqa.selenium.devtools.v102.network.model.Cookie> getAllCookies() {
        log.debug("Получить список cookies");
        return Kraken.getDevTools().send(Network.getAllCookies());
    }

    public static void deleteCookie(final org.openqa.selenium.devtools.v102.network.model.Cookie cookie) {
        log.debug("Удалить куку {}", cookie);
        Kraken.getDevTools().send(Network.deleteCookies(
                cookie.getName(),
                Optional.empty(),
                Optional.of(cookie.getDomain()),
                Optional.of(cookie.getPath()))
        );
    }

    public static void deleteAllCookies() {
        log.debug("Удалить все cookies");
        Kraken.getDevTools().send(Network.clearBrowserCookies());
    }

    public static void addCookie(final Cookie cookie) {
        log.debug("Добавить куку {}", cookie);
        Kraken.getDevTools().send(Network.setCookie(
                cookie.getName(),
                cookie.getValue(),
                Optional.empty(),
                Optional.of(cookie.getDomain()),
                Optional.of(cookie.getPath()),
                Optional.of(false),
                Optional.of(false),
                Optional.empty(),
                cookie.getExpiry() == null ? Optional.empty() : Optional.of(new TimeSinceEpoch(cookie.getExpiry().getTime()/1000)),
                Optional.of(CookiePriority.MEDIUM),
                Optional.of(false),
                Optional.of(CookieSourceScheme.SECURE),
                Optional.of(443),
                Optional.empty()
        ));
    }

    public static void addCookies(final Collection<Cookie> cookies) {
        cookies.forEach(CdpCookie::addCookie);
    }
}
