package ru.instamart.reforged.core.page;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.*;
import static ru.instamart.kraken.util.StringUtil.failMessage;

public interface PageCheck extends PageElement {

    @Step("Проверка доступности текущей страницы")
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

    @Step("Проверка что текущая страница недоступна")
    default void checkPageIsUnavailable() throws AssertionError {
        assertTrue(
                Kraken.is404(),
                failMessage("Нет ожидаемой ошибки 404 на странице " + Kraken.currentUrl())
        );
    }

    @Step("Проверка что находимся на странице '{0}'")
    default void checkPageUrl(final String url) {
        Assert.assertTrue(Kraken.waitAction().isUrlEquals(url));
    }

    @Step("Проверка что страница '{0}' не открылась")
    default void checkForbiddenPageUrl(final String url) {
        assertNotEquals(Kraken.currentUrl(), url, "Текущая страница должна быть недоступна");
    }

    @Step("Проверка что страница содержит часть url '{0}'")
    default void checkPageContains(final String expectedUrl) {
        Assert.assertTrue(Kraken.waitAction().isUrlContains(expectedUrl),
                String.format("Адрес текущей страницы: '%s' не содержит ожидаемый фрагмент: '%s'", Kraken.currentUrl(), expectedUrl));
    }

    @Step("Ожидание загрузки страницы DOM и Network")
    default void waitPageLoad() {
        Assert.assertTrue(Kraken.jsAction().waitForDocumentReady(), "DOM не в статусе ready");
        Assert.assertTrue(Kraken.jsAction().checkPendingRequests(), "Запросы из нетворка остались в статусе pinding");
    }

    @Step("Проверяем, что страница открылась (отсутствует сообщение об ошибке)")
    default void checkPageOpened() {
        page404Error.should().invisible();
    }

    @Step("Проверяем, что страница не открылась (отображается ошибка 'Ничего не нашлось')")
    default void checkPageError404() {
        page404Error.should().visible();
    }

    @Step("Проверяем, что на странице открылся фрейм")
    default void checkFrameOpened() {
        Kraken.waitAction().frameShouldBeVisible(0);
    }
}
