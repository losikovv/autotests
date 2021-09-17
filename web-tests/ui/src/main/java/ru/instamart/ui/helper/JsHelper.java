package ru.instamart.ui.helper;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

@Slf4j
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
                "     url : '"+ EnvironmentProperties.Env.FULL_SITE_URL + "api/user_sessions',\n" +
                "     method : 'delete'\n" +
                "});");
        ajaxReady();
    }

    /**
     * @return - Получение списка данных из localStorage
     */
    public static String getLocalStorage() {
        final Object o = execute("return window.localStorage");
        return String.valueOf(o);
    }

    @SuppressWarnings("unchecked")
    public static <T> T execute(final String js, final Object... arguments) {
        try {
            return (T) ((JavascriptExecutor) getWebDriver()).executeScript(js, arguments);
        } catch (Exception e) {
            log.error("Fail when execute js code {}", js);
        }
        return null;
    }

    private JsHelper() {}
}
