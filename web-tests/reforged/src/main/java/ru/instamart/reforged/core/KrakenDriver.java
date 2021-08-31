package ru.instamart.reforged.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import ru.instamart.reforged.core.service.WebDriverContainer;

class KrakenDriver {

    private static final WebDriverContainer CONTAINER = new WebDriverContainer();

    public static void refresh() {
        getWebDriver().navigate().refresh();
    }

    public static void back() {
        getWebDriver().navigate().back();
    }

    public static void forward() {
        getWebDriver().navigate().forward();
    }

    public static String title() {
        return getWebDriver().getTitle();
    }

    public static String getSource() {
        return getWebDriver().getPageSource();
    }

    public static SessionId getSessionId() {
        return ((RemoteWebDriver)getWebDriver()).getSessionId();
    }

    public static WebDriver getWebDriver() {
        return CONTAINER.createOrGetDriver();
    }

    public static void closeWebDriver() {
        CONTAINER.closeDriver();
    }
}
