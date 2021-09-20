package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.*;
import static ru.instamart.kraken.util.StringUtil.failMessage;

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
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
    }

    @Step("Добавить куки {0}")
    default void addCookie(final Cookie cookie) {
        Kraken.addCookie(cookie);
    }

    @Step("Скроллим страницу вверх")
    default void scrollUp() {
        Kraken.jsAction().scrollToTheTop();
    }

    @Step("Скроллим страницу вниз")
    default void scrollDown() {
        Kraken.jsAction().scrollToTheBottom();
    }

    @Step("Подтверждаем браузерный алерт")
    default void confirmBrowserAlert() {
        Kraken.alertConfirm();
    }

    @Step("Отклоняем браузерный алерт")
    default void declineBrowserAlert() {
        Kraken.alertDismiss();
    }

    @Step("Проверяем доступность текущей страницы")
    default void checkPageIsAvailable() throws AssertionError {
        assertFalse(
                Kraken.is404(),
                failMessage("Ошибка 404 на странице " + Kraken.currentUrl())
        );
        assertFalse(
                Kraken.is500(),
                failMessage("Ошибка 500 на странице " + Kraken.currentUrl())
        );
        assertFalse(
                Kraken.is502(),
                failMessage("Ошибка 502 на странице " + Kraken.currentUrl())
        );
    }

    @Step("Проверяем что страница недоступна")
    default void checkPageIsUnavailable() throws AssertionError {
        assertTrue(
                Kraken.is404(),
                failMessage("Нет ожидаемой ошибки 404 на странице " + Kraken.currentUrl())
        );
    }

    @Step("Проверяем что находимся на ожидаемой странице")
    default void checkPageUrl(final String url) {
        Kraken.waitAction().urlEquals(url);
    }

    @Step("Проверяем что страница не открылась")
    default void checkForbiddenPageUrl(final String url) {
        assertNotEquals(Kraken.currentUrl(), url, "Текущая страница должна быть недоступна");
    }
}
