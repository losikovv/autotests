package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.cdp.CdpCookie;

import java.util.HashSet;
import java.util.Set;

import static ru.instamart.reforged.core.Kraken.*;

public interface Page extends PageCheck {

    String pageUrl();

    @Step("Открыть страницу {0} в админке")
    default void openAdminPage(final String page) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + page);
    }

    @Step("Открыть страницу {0} в админке")
    default void openAdminPageWithoutSpa(final String page) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD + page);
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
        CdpCookie.addCookie(cookie);
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

    /**
     * Добавляет или подменяет куки на нужные
     */
    default void cookiesChange(final boolean isFixedUUID) {
        Set<Cookie> cookies = new HashSet<>();
        if (isFixedUUID) cookies.add(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE);
        if (EnvironmentProperties.Env.isProduction()) cookies.add(CookieFactory.USER_ADULT_18_PLUS_ALERT);
        cookies.add(CookieFactory.RETAILERS_REMINDER_MODAL);
        addOrReplaceCookies(cookies);
    }

    /**
     * Добавляет куку
     */
    default void cookieChange(final Cookie cookie) {
        CdpCookie.addCookie(cookie);
    }

    /**
     * Добавляет или подменяет куку на нужную
     */
    default void cookieReplace(final Cookie cookie) {
        addOrReplaceCookie(cookie);
    }

    /**
     * Проверяет наличие нескольких кук и подменяет их на нужные
     */
    default void cookiesReplace(final Set<Cookie> cookies) {
        addOrReplaceCookies(cookies);
    }

    /**
     * Подменяет определенное местоположение на указанный регион
     *
     * @param cityName - название города, на английском: Moscow, Novosibirsk, Barnaul
     */
    default void setLocation(final String cityName) {
        cookieReplace(CookieFactory.setLocation(cityName));
    }
}
