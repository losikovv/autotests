package ru.instamart.reforged.core.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static ru.instamart.reforged.core.Kraken.execute;
import static ru.instamart.reforged.core.service.KrakenDriver.getWebDriver;

@Slf4j
public final class JsAction {

    public static void reactReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.pollingEvery(250, TimeUnit.MILLISECONDS);
        wait.until((ExpectedCondition<Boolean>) wb -> {
            final String result = String.valueOf(execute("return typeof ReactRailsUJS.components"));
            log.debug("React status is {}", result);
            return result.equals("object");
        });
    }

    public static void ymapReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.pollingEvery(250, TimeUnit.MILLISECONDS);
        wait.until((ExpectedCondition<Boolean>) wb -> {
            final String result = String.valueOf(execute("return typeof ymaps"));
            log.debug("ymap status is {}", result);
            return result.equals("object");
        });
    }

    public static void waitForDocumentReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return document.readyState").toString().equals("complete"));
    }

    public static void scrollToTheTop() {
        execute("scrollTo(0,0)");
    }

    /**
     * Скролл до элемента
     * @param locator - локатор достается из компонента через регулярку {@link ru.instamart.reforged.core.component.Component}
     */
    public static void scrollToElement(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
    }

    public static void scrollToTheBottom() {
        execute("scrollTo(0,document.body.scrollHeight)");
    }

    public static void clearField(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).setAttribute('value', '');");
    }

    public static void setCookieValue(final String name, final String value) {
        execute("document.cookie=\""+name+"="+value+"\"");
    }

    /**
     * Клик в первый элемент соответствующий xpath
     * @param locator - элемент в который нужно кликнуть
     */
    public static void hoverAndClick(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    private JsAction() {}
}
