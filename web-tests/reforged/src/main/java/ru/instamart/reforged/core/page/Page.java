package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.cdp.CdpCookie;

public interface Page extends PageCheck {

    String BASIC_AUTH = CoreProperties.BASIC_AUTH_USERNAME + ":" + CoreProperties.BASIC_AUTH_PASSWORD + "@";

    String pageUrl();

    @Step("Открыть страницу {0} в админке")
    default void openAdminPage(final String page) {
        Kraken.open(addBasicAuthToUrl(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + page));
    }

    @Step("Открыть страницу {0} в админке")
    default void openAdminPageWithoutSpa(final String page) {
        Kraken.open(addBasicAuthToUrl(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD + page));
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
        Kraken.open(addBasicAuthToUrl(currentUrl));
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
     * Добавляет куку
     */
    default void cookieChange(final Cookie cookie) {
        CdpCookie.addCookie(cookie);
    }

    /**
     * Подменяет определенное местоположение на указанный регион
     *
     * @param cityName - название города, на английском: Moscow, Novosibirsk, Barnaul
     */
    default void setLocation(final String cityName) {
        CdpCookie.addCookie(CookieFactory.setLocation(cityName));
        refresh();
    }

    default String addBasicAuthToUrl(final String url) {
        return url.replace("://", "://" + BASIC_AUTH);
    }
}
