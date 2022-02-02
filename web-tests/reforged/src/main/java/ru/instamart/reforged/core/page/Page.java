package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;

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

    @Step("Обновить страницу")
    default void refresh() {
        Kraken.refresh();
    }

    @Step("Обновить страницу, обрезав basic auth - костыльный шаг от разлогина и ошибок бейсика")
    default void refreshWithoutBasicAuth() {
        final var currentUrl = Kraken.getWebDriver().getCurrentUrl();
        Kraken.getWebDriver().get(StringUtil.cutBasicAuthFromUrl(currentUrl));
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
