package ru.instamart.reforged.core;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.core.action.WaitAction;
import ru.instamart.reforged.core.cdp.CdpCookie;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class Kraken extends KrakenDriver {

    private static final JsAction jsAction = new JsAction();
    private static final WaitAction wait = new WaitAction();

    @Step("Переход на страницу {0}")
    public static void open(final String url) {
        log.debug("Переход на страницу {}", url);
        getWebDriver().get(url);
    }

    public static void closeWindow() {
        log.debug("Закрыть окно браузера");
        getWebDriver().close();
    }

    public static void closeBrowser() {
        log.debug("Закрыть браузер");
        closeWebDriver();
    }

    public static String currentUrl() {
        log.debug("Получить текущий url страницы");
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
        log.debug("Подтвердить алерт");
        getWebDriver().switchTo().alert().accept();
    }

    public static void alertDismiss() {
        log.debug("Отклонить алерт");
        getWebDriver().switchTo().alert().dismiss();
    }

    public static void switchFrame(final int index) {
        log.debug("Переключиться на фрейм с индексом {}", index);
        waitAction().frameShouldBeVisible(index);
    }

    public static void switchToParentFrame() {
        log.debug("Переключиться на родительский фрейм");
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
        log.debug("Получить лог с типом {}", logType);
        return getAllLogs().get(logType);
    }

    public static void addCookieIfNotExist(final Cookie cookie) {
        final var cookies = CdpCookie.getAllCookies()
                .stream()
                .filter(c -> c.getName().equals(cookie.getName())
                        && (!c.getValue().equals(cookie.getValue())
                        || c.getExpires().longValue() != cookie.getExpiry().getTime()/1000))
                .collect(Collectors.toSet());

        cookies.forEach(c -> {
            CdpCookie.deleteCookie(c);
            CdpCookie.addCookie(cookie);
            refresh();
        });
    }

    public static void addCookiesIfNotExist(final Set<Cookie> newCookies) {
        final var listOfCookies = CdpCookie.getAllCookies()
                .stream()
                .filter(c -> newCookies.stream().anyMatch(a ->
                        a.getName().equals(c.getName())
                                && (!a.getValue().equals(a.getValue())
                                || a.getExpiry().getTime()/1000 != c.getExpires().longValue())))
                .collect(Collectors.toSet());

        if (listOfCookies.size() > 0) {
            listOfCookies.forEach(CdpCookie::deleteCookie);
            newCookies.forEach(CdpCookie::addCookie);
            refresh();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T execute(final String js, final Object... arguments) {
        log.debug("Execute script {}", js);
        try {
            return (T) ((JavascriptExecutor) getWebDriver()).executeScript(js, arguments);
        } catch (Exception e) {
            log.error("Fail when execute js code {}", js);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T executeAsync(final String js, final Object... arguments) {
        log.debug("Execute async script {}", js);
        try {
            return (T) ((JavascriptExecutor) getWebDriver()).executeAsyncScript(js, arguments);
        } catch (Exception e) {
            log.error("Fail when execute async js code {}", js);
        }
        return null;
    }
}
