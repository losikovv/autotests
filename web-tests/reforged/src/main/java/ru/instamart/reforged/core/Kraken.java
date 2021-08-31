package ru.instamart.reforged.core;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.core.action.WaitAction;

import java.util.Set;

@Slf4j
public final class Kraken extends KrakenDriver {

    private static final JsAction jsAction = new JsAction();
    private static final WaitAction wait = new WaitAction();

    public static void open(final String url) {
        log.info("Переход на страницу {}", url);
        getWebDriver().get(url);
    }

    public static void closeWindow() {
        getWebDriver().close();
    }

    public static void closeBrowser() {
        closeWebDriver();
    }

    public static String currentUrl() {
        return getWebDriver().getCurrentUrl();
    }

    public static boolean is404() {
        return title().contains("404");
    }

    public static boolean is500() {
        return title().contains("500");
    }

    public static boolean is502() {
        return title().contains("502");
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
        waitAction().frameShouldBeVisible(index);
    }

    public static void switchToParentFrame() {
        getWebDriver().switchTo().parentFrame();
    }

    public static Actions action() {
        return new Actions(getWebDriver());
    }

    public static JsAction jsAction() {
        return jsAction;
    }

    public static WaitAction waitAction() {
        return wait;
    }

    public static LogEntries getLogs(final String logType) {
        return getAllLogs().get(logType);
    }

    public static void addCookie(final Cookie cookie) {
        getWebDriver().manage().addCookie(cookie);
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

    public static Set<Cookie> getCookie() {
        return getWebDriver().manage().getCookies();
    }
}
