package ru.instamart.reforged.action;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

public final class JsAction {

    public static void waitForAjaxToFinish() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return jQuery.active == 0").equals(true));
    }

    public static void scrollToTheBottom() {
        execute("scroll(0, 500)");
    }

    /**
     * Клик в первый элемент соответствующий xpath
     * @param xpath - элемент в который нужно кликнуть
     */
    public static void hoverAndClick(final String xpath) {
        execute("document.evaluate(\""+xpath+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    private static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    private JsAction() {}
}
