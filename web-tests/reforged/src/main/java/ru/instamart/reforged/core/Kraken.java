package ru.instamart.reforged.core;

import org.openqa.selenium.*;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.service.KrakenDriver;

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

    public static Logs logs() {
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

    public static Object execute(final String js) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(js);
    }

    public static Set<Cookie> getCookie() {
        return getWebDriver().manage().getCookies();
    }

    public static FluentWait<WebDriver> createWait(final Component component) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(component.getTimeout(), TimeUnit.SECONDS)
                .withMessage(component.getErrorMsg())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    public static FluentWait<WebDriver> createWait(final int wait, final String errorMsg) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(wait, TimeUnit.SECONDS)
                .withMessage(errorMsg)
                .pollingEvery(250, TimeUnit.MILLISECONDS);
    }
}
