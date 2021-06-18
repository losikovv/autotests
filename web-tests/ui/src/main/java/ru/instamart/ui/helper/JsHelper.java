package ru.instamart.ui.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

public final class JsHelper {

    public static void ymapReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return typeof ymaps").equals("object"));
    }

    private static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    private JsHelper() {}
}
