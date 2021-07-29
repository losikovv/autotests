package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.kraken.util.StringUtil.failMessage;

public interface Page extends PageCheck {

    String pageUrl();

    @Step("Открыть страницу {0} в админке")
    default void openAdminPage(final String page) {
        Kraken.open(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + page);
    }

    @Step("Открыть страницу {0} на сайте")
    default void openSitePage(final String page) {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + page);
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
        Assert.assertFalse(
                Kraken.is404(),
                failMessage("Ошибка 404 на странице " + Kraken.currentUrl())
        );
        Assert.assertFalse(
                Kraken.is500(),
                failMessage("Ошибка 500 на странице " + Kraken.currentUrl())
        );
        Assert.assertFalse(
                Kraken.is502(),
                failMessage("Ошибка 502 на странице " + Kraken.currentUrl())
        );
    }

    @Step("Проверяем что находимся на ожидаемой странице")
    default void checkPageUrl(final String url) {
        Kraken.waitAction().urlEquals(url);
    }

    @Step("Проверяем что страница не открылась")
    default void checkForbiddenPageUrl(final String url) {
        Assert.assertNotEquals(Kraken.currentUrl(), url, "Текущая страница должна быть недоступна");
    }
}
