package ru.instamart.reforged.core.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.component.AbstractComponent;
import ru.instamart.reforged.core.config.WaitProperties;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.instamart.reforged.core.Kraken.*;

@Slf4j
public final class JsAction {

    /**
     * Ожидание инициализации реактовского jQuery
     */
    public void jQueryReady() {
        createWait().until((ExpectedCondition<Boolean>) wb -> {
            final Object reactState = execute("return ReactRailsUJS.jQuery.active==0");
            if (Objects.isNull(reactState)) {
                return false;
            }
            return (Boolean) reactState;
        });
    }

    /**
     * Ожидание загрузки дома
     */
    public void waitForDocumentReady() {
        createWait().until((ExpectedCondition<Boolean>) wb -> {
            final Object state = execute("return document.readyState");
            if (Objects.isNull(state)) {
                return false;
            }
            return state.equals("complete");
        });
    }

    /**
     * Ожидание загрузки картинки
     */
    public void waitImgLoad(final String xpath) {
        createWait().until((ExpectedCondition<Boolean>) wb -> {
            final Object loadState = execute("return document.evaluate(\""+ xpath +"\", document, null, XPathResult.ANY_TYPE, null).iterateNext().complete;");
            if (Objects.isNull(loadState)) {
                return false;
            }
            return (Boolean) loadState;
        });
    }

    public void scrollToTheTop() {
        execute("scrollTo(0,0)");
    }

    /**
     * Скролл до элемента
     * @param locator - локатор достается из компонента через регулярку {@link AbstractComponent}
     */
    public void scrollToElement(final String locator) {
        execute("document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView(true);");
    }

    public void scrollToTheBottom() {
        execute("scrollTo(0,document.body.scrollHeight)");
    }

    public void clearField(final String locator) {
        execute("document.evaluate(\"" + locator + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value = '';");
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

    public void setValueReact(final String locator, final String value) {
        execute(String.format("document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value = '%s';", locator, value));
    }

    public void setValue(final WebElement element, String text) {
        if (isNull(text) || text.isEmpty()) {
            element.clear();
        }
        final String error = setValueByJs(element, text);

        if (nonNull(error)) {
            throw new InvalidElementStateException(error);
        } else {
            execute("var element = arguments[0];\n" +
                    "var eventNames = arguments[1];\n" +
                    "for (var i = 0; i < eventNames.length; i++) {" +
                    "  if (document.createEventObject) {\n" +  // IE
                    "    var evt = document.createEventObject();\n" +
                    "    element.fireEvent('on' + eventNames[i], evt);\n" +
                    "  }\n" +
                    "  else {\n" +
                    "    var evt = document.createEvent('HTMLEvents');\n " +
                    "    evt.initEvent(eventNames[i], true, true );\n " +
                    "    element.dispatchEvent(evt);\n" +
                    "  }\n" +
                    '}',
                    element, "keydown", "keypress", "input", "keyup", "change");
        }
    }

    private String setValueByJs(final WebElement element, final String text) {
        return execute(
                "return (function(element, text) {" +
                        "if (element.getAttribute('readonly') != undefined) return 'Cannot change value of readonly element';" +
                        "if (element.getAttribute('disabled') != undefined) return 'Cannot change value of disabled element';" +
                        "element.focus();" +
                        "var maxlength = element.getAttribute('maxlength') == null ? -1 : parseInt(element.getAttribute('maxlength'));" +
                        "element.value = " +
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
                "     url : '"+ EnvironmentProperties.Env.FULL_SITE_URL + "api/user_sessions',\n" +
                "     method : 'delete'\n" +
                "});");
        jsAction().jQueryReady();
    }

    /**
     * @return - Получение списка данных из localStorage
     */
    public String getLocalStorage() {
        final Object o = execute("return window.localStorage");
        return String.valueOf(o);
    }

    public void ajaxRequest(final String endpoint, final String method) {
        execute(String.format("$.ajax({url : '%s', method : '%s'});", EnvironmentProperties.Env.FULL_SITE_URL + endpoint, method));
    }

    public void checkPendingRequests() {
        var wait = createWait();
        wait.until((ExpectedCondition<Boolean>) wb -> {
            final Object pendingRequest = execute("return window.pendingRequest");
            if (pendingRequest instanceof Long) {
                final Long countRequest = (Long) pendingRequest;
                final  Object urls = execute("return window.urls");
                if (Objects.nonNull(urls)) {
                    wait.withMessage("Wait pending urls: " + urls);
                }
                return countRequest == 0L;
            } else {
                patchForPendingRequest();
                return false;
            }
        });
    }

    /**
     * Создаем патч на странице для прослушивания реквестов и сохранения их урлов и статуса.
     * Если реквест выполняется листнер удаляет урл и декриментит счетчик
     */
    private void patchForPendingRequest() {
        final String script =
                "(function() {" +
                    "var oldOpen = XMLHttpRequest.prototype.open;" +
                    "window.urls = [];" +
                    "window.pendingRequest = 0;" +
                    "XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {" +
                        "window.pendingRequest++;" +
                        "window.urls.push(url);" +
                        "this.addEventListener('readystatechange', function() {" +
                            "if(this.readyState == 4) {" +
                                "window.pendingRequest--;" +
                                "const index = window.urls.indexOf(url);\n" +
                                "if (index > -1) {\n" +
                                    "  window.urls.splice(index, 1);\n" +
                                "}" +
                            "}" +
                        "}, false);" +
                        "oldOpen.call(this, method, url, async, user, pass);" +
                    "}" +
                "})();";
        execute(script);
    }

    private FluentWait<WebDriver> createWait() {
        return new WebDriverWait(getWebDriver(), WaitProperties.BASIC_TIMEOUT)
                .pollingEvery(WaitProperties.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }
}
