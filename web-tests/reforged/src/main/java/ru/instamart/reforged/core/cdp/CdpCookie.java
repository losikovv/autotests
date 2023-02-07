package ru.instamart.reforged.core.cdp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.devtools.v109.network.Network;
import org.openqa.selenium.devtools.v109.network.model.*;
import ru.instamart.reforged.core.Kraken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public final class CdpCookie {

    public static List<org.openqa.selenium.devtools.v109.network.model.Cookie> getAllCookies() {
        log.debug("Получить список cookies");
        return Kraken.getDevTools().send(Network.getAllCookies());
    }

    public static void deleteCookie(final Cookie cookie) {
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
                isNull(cookie.getExpiry()) ? Optional.empty() : Optional.of(new TimeSinceEpoch(cookie.getExpiry().getTime()/1000)),
                Optional.of(CookiePriority.MEDIUM),
                Optional.of(false),
                Optional.of(CookieSourceScheme.SECURE),
                Optional.of(443),
                Optional.empty()
        ));
    }

    public static void addCookies(final Collection<Cookie> cookies) {
        final var cookiesParams = new ArrayList<CookieParam>();
        cookies.forEach(c -> {
            final var cookie = new CookieParam(
                    c.getName(),
                    c.getValue(),
                    Optional.empty(),
                    Optional.of(c.getDomain()),
                    Optional.of(c.getPath()),
                    Optional.of(c.isSecure()),
                    Optional.of(c.isHttpOnly()),
                    isNull(c.getSameSite()) ? Optional.empty() : Optional.of(CookieSameSite.fromString(c.getSameSite())),
                    isNull(c.getExpiry()) ? Optional.empty() : Optional.of(new TimeSinceEpoch(c.getExpiry().getTime() / 1000)),
                    Optional.of(CookiePriority.MEDIUM),
                    Optional.of(false),
                    Optional.of(CookieSourceScheme.SECURE),
                    Optional.of(443),
                    Optional.empty()
            );
            log.debug("Добавить куку {}", ToStringBuilder.reflectionToString(cookie));
            cookiesParams.add(cookie);
        });
        if (!cookiesParams.isEmpty())
            Kraken.getDevTools().send(Network.setCookies(cookiesParams));
    }
}
