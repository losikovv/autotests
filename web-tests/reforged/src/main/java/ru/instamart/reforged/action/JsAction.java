package ru.instamart.reforged.action;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

public final class JsAction {

    public static void ymapReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return typeof ymaps").equals("object"));
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
     * @param locator - локатор достается из компонента через регулярку {@link ru.instamart.reforged.stf.component.Component}
     */
    public static void scrollToElement(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
    }

    public static void scrollToTheBottom() {
        execute("scrollTo(0,document.body.scrollHeight)");
    }

    /**
     * Клик в первый элемент соответствующий xpath
     * @param locator - элемент в который нужно кликнуть
     */
    public static void hoverAndClick(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    private static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    private JsAction() {}
}
