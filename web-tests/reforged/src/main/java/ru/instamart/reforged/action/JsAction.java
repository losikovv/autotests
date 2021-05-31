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

    //document.querySelectorAll('[data-qa="increase-button"]');
    public static void hoverAndClick(final String dataQaValue) {
        execute("document.querySelectorAll('[data-qa=\""+dataQaValue+"\"]')[0].click();");
    }

    private static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    private JsAction() {}
}
