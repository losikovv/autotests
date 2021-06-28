package ru.instamart.reforged.core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.core.action.WaitAction;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.service.KrakenDriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static ru.instamart.reforged.core.service.KrakenDriver.closeWebDriver;
import static ru.instamart.reforged.core.service.KrakenDriver.getWebDriver;

public final class Kraken {

    public static void open(final String url) {
        getWebDriver().get(url);
    }

    public static void close() {
        closeWebDriver();
    }

    public static Logs getAllLogs() {
        return getWebDriver().manage().logs();
    }

    public static void alertConfirm() {
        getWebDriver().switchTo().alert().accept();
    }

    public static void alertDismiss() {
        getWebDriver().switchTo().alert().dismiss();
    }

    public static void switchFrame(final int index) {
        getWebDriver().switchTo().frame(index);
    }

    public static void switchToParentFrame() {
        getWebDriver().switchTo().parentFrame();
    }

    public static Actions action() {
        return new Actions(getWebDriver());
    }

    public static JsAction jsAction() {
        return JsAction.INSTANCE;
    }

    public static WaitAction waitAction() {
        return WaitAction.INSTANCE;
    }

    public static LogEntries getLogs(final String logType) {
        return getWebDriver().manage().logs().get(logType);
    }

    public static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    public static Set<Cookie> getCookie() {
        return getWebDriver().manage().getCookies();
    }
}
