package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

import java.util.Set;

public interface StfPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + url);
    }

    /**
     * Проверят что есть кука {@link CookieFactory#EXTERNAL_ANALYTICS_ANONYMOUS_ID}
     * после чего пытается её обновить на куку с исключённым из всех АБ тестов anonymousId
     */
    default void excludeGuestFromAllAb() {
        Kraken.waitAction().cookieShouldBeExist(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID.getName());
        Kraken.addCookieIfNotExist(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID);
    }

    /**
     * Проверяет наличие нескольких кук и подменяет их на нужные
     */
    default void cookiesChange(final boolean isFixedUUID) {
        final var abCookie = isFixedUUID ? CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE : CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID;
        Kraken.waitAction()
                .cookiesShouldBeExist(
                        Set.of(abCookie.getName(), CookieFactory.RETAILERS_REMINDER_MODAL.getName())
                );
        Kraken.addCookiesIfNotExist(Set.of(abCookie, CookieFactory.RETAILERS_REMINDER_MODAL));
    }
}
