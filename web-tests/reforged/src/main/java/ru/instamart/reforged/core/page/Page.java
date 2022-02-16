package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;
import java.util.stream.Collectors;

public interface Page extends PageCheck {

    String pageUrl();

    @Step("Открыть страницу {0} в админке")
    default void openAdminPage(final String page) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + page);
    }

    @Step("Открыть страницу {0} на сайте")
    default void openSitePage(final String page) {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + page);
    }

    @Step("Открыть страницу {0} на B2C сайте")
    default void openStfPage(final String page) {
        Kraken.open(EnvironmentProperties.Env.FULL_B2C_URL_WITH_BASIC_AUTH + page);
    }

    @Step("Обновить страницу")
    default void refresh() {
        Kraken.refresh();
    }

    @Step("Обновить страницу, обрезав basic auth - костыльный шаг от разлогина и ошибок бейсика")
    default void refreshWithoutBasicAuth() {
        final var currentUrl = Kraken.getWebDriver().getCurrentUrl();
        Kraken.open(StringUtil.cutBasicAuthFromUrl(currentUrl));
    }

    //TODO при открытии бизнес-витрины по клику "Покупать для бизнеса" на stf мешает базовая авторизация
    @Step("Обновить страницу, добавив basic auth")
    default void refreshWithBasicAuth() {
        final var currentUrl = Kraken.getWebDriver().getCurrentUrl();
        Kraken.open(StringUtil.addBasicAuthToUrl(currentUrl));
    }

    @Step("Добавить куки {0}")
    default void addCookie(final Cookie cookie) {
        Kraken.addCookie(cookie);
    }

    @Step("Скролл страницы вверх")
    default void scrollUp() {
        Kraken.jsAction().scrollToTheTop();
    }

    @Step("Скролл страницы вниз")
    default void scrollDown() {
        Kraken.jsAction().scrollToTheBottom();
    }

    @Step("Подтвердить браузерный алерт")
    default void confirmBrowserAlert() {
        Kraken.alertConfirm();
    }

    @Step("Отклонить браузерный алерт")
    default void declineBrowserAlert() {
        Kraken.alertDismiss();
    }

    /**
     * Проверят что есть кука {@link CookieFactory#EXTERNAL_ANALYTICS_ANONYMOUS_ID}
     * после чего пытается её обновить на куку с исключённым из всех АБ тестов anonymousId
     */
    default void excludeGuestFromAllAb() {
        cookieChange(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID);
    }

    default void cookiesChange(final boolean isFixedUUID) {
        final var abCookie = isFixedUUID ? CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE : CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID;
        cookiesChange(Set.of(abCookie, CookieFactory.RETAILERS_REMINDER_MODAL));
    }

    /**
     * Проверяет наличие куки и подменяет ее на нужную
     */
    default void cookieChange(final Cookie cookie) {
        checkPageIsAvailable();
        Kraken.waitAction().cookieShouldBeExist(cookie.getName());
        Kraken.addCookieIfNotExist(cookie);
    }

    /**
     * Проверяет наличие нескольких кук и подменяет их на нужные
     */
    default void cookiesChange(final Set<Cookie> cookies) {
        checkPageIsAvailable();
        Kraken.waitAction().cookiesShouldBeExist(cookies.stream().map(Cookie::getName).collect(Collectors.toSet()));
        Kraken.addCookiesIfNotExist(cookies);
    }
}
