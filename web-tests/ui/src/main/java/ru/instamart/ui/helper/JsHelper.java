package ru.instamart.ui.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

public final class JsHelper {

    public static void ymapReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return typeof ymaps").equals("object"));
    }

    public static void ajaxReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> (Boolean) execute("return jQuery.active==0"));
    }

    public static void scrollToElement(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
    }

    public static void setCookieValue(final String name, final String value) {
        execute("document.cookie=\""+name+"="+value+"\"");
    }

    public static void hoverAndClick(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    public static void clearField(final String locator) {
        execute("document.evaluate(\""+locator+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).setAttribute('value', '');");
    }

    public static void clearSession() {
        execute("$.ajax({\n" +
                "     url : '"+ EnvironmentData.INSTANCE.getBasicUrl() + "api/user_sessions',\n" +
                "     method : 'delete'\n" +
                "});");
        ajaxReady();
    }

    private static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    private JsHelper() {}
}
