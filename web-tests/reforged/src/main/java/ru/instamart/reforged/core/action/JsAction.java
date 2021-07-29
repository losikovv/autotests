package ru.instamart.reforged.core.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.instamart.reforged.core.Kraken.execute;
import static ru.instamart.reforged.core.service.KrakenDriver.getWebDriver;

@Slf4j
public final class JsAction {

    /**
     * Ожидание инициализации яндекс карт
     */
    public void ymapReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), Config.BASIC_TIMEOUT);
        wait.pollingEvery(Config.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
        wait.until((ExpectedCondition<Boolean>) wb -> {
            final String result = String.valueOf(execute("return typeof ymaps"));
            log.debug("ymap status is {}", result);
            return result.equals("object");
        });
    }

    /**
     * Ожидание инициализации реактовского jQuery
     */
    public void jQueryReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), Config.BASIC_TIMEOUT);
        wait.pollingEvery(Config.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
        wait.until((ExpectedCondition<Boolean>) wb -> (Boolean) execute("return ReactRailsUJS.jQuery.active==0"));
    }

    /**
     * Ожидание инициализации ajax
     */
    public static void ajaxReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until((ExpectedCondition<Boolean>) wb -> (Boolean) execute("return jQuery.active==0"));
    }

    /**
     * Ожидание загрузки дома
     */
    public void waitForDocumentReady() {
        final WebDriverWait wait = new WebDriverWait(getWebDriver(), Config.BASIC_TIMEOUT);
        wait.pollingEvery(Config.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
        wait.until((ExpectedCondition<Boolean>) wb -> execute("return document.readyState").toString().equals("complete"));
    }

    public void scrollToTheTop() {
        execute("scrollTo(0,0)");
    }

    /**
     * Скролл до элемента
     * @param locator - локатор достается из компонента через регулярку {@link ru.instamart.reforged.core.component.Component}
     */
    public void scrollToElement(final String locator) {
        execute("document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
    }

    public void scrollToTheBottom() {
        execute("scrollTo(0,document.body.scrollHeight)");
    }

    public void clearField(final String locator) {
        execute("document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).setAttribute('value', '');");
    }

    public void click(final WebElement element) {
        execute("arguments[0].click()", element);
    }

    public void clearLocalStorage() {
        execute("localStorage.clear();");
    }

    public void setCookieValue(final String name, final String value) {
        execute("document.cookie=\"" + name + "=" + value + "\"");
    }

    public void setValue(final WebElement element, String text) {
        if (isNull(text) || text.isEmpty()) {
            element.clear();
        }
        final String error = setValueByJs(element, text);

        if (nonNull(error)) {
            throw new InvalidElementStateException(error);
        } else {
            execute("var webElement = arguments[0];\n" +
                    "var eventNames = arguments[1];\n" +
                    "for (var i = 0; i < eventNames.length; i++) {" +
                    "  if (document.createEventObject) {\n" +  // IE
                    "    var evt = document.createEventObject();\n" +
                    "    webElement.fireEvent('on' + eventNames[i], evt);\n" +
                    "  }\n" +
                    "  else {\n" +
                    "    var evt = document.createEvent('HTMLEvents');\n " +
                    "    evt.initEvent(eventNames[i], true, true );\n " +
                    "    webElement.dispatchEvent(evt);\n" +
                    "  }\n" +
                    '}',
                    element, "keydown", "keypress", "input", "keyup", "change");
        }
    }

    private String setValueByJs(final WebElement element, final String text) {
        return execute(
                "return (function(webelement, text) {" +
                        "if (webelement.getAttribute('readonly') != undefined) return 'Cannot change value of readonly element';" +
                        "if (webelement.getAttribute('disabled') != undefined) return 'Cannot change value of disabled element';" +
                        "webelement.focus();" +
                        "var maxlength = webelement.getAttribute('maxlength') == null ? -1 : parseInt(webelement.getAttribute('maxlength'));" +
                        "webelement.value = " +
                        "maxlength == -1 ? text " +
                        ": text.length <= maxlength ? text " +
                        ": text.substring(0, maxlength);" +
                        "return null;" +
                        "})(arguments[0], arguments[1]);",
                element, text);
    }

    /**
     * Клик в первый элемент соответствующий xpath
     * @param locator - элемент в который нужно кликнуть
     */
    public void hoverAndClick(final String locator) {
        execute("document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
    }

    /**
     * Очистка сессии
     */
    public static void clearSession() {
        execute("$.ajax({\n" +
                "     url : '"+ EnvironmentData.INSTANCE.getBasicUrl() + "api/user_sessions',\n" +
                "     method : 'delete'\n" +
                "});");
        ajaxReady();
    }

    /**
     * Получение списка данных из localStorage
     * @return
     */
    public String getLocalStorage() {
        final Object o = execute("return window.localStorage");
        return String.valueOf(o);
    }

    public void ajaxRequest(final String endpoint, final String method) {
        execute(String.format("$.ajax({url : '%s', method : '%s'});", EnvironmentData.INSTANCE.getBasicUrl() + endpoint, method));
    }
}
