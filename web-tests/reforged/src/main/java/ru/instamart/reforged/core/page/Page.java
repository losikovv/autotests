package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.StringUtil;
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

    @Step("Обновить страницу, обрезав basic auth - костыльный шаг от разлогина и ошибок бейсика")
    default void refreshWithoutBasicAuth() {
        String currentUrl = Kraken.getWebDriver().getCurrentUrl();
        Kraken.getWebDriver().get(StringUtil.cutBasicAuthFromUrl(currentUrl));
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
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
}
