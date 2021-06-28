package ru.instamart.reforged.core.service;

import org.openqa.selenium.WebDriver;

public final class KrakenDriver {

    private static final WebDriverContainer container = new WebDriverContainer();

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

    public static WebDriver getWebDriver() {
        return container.createOrGetDriver();
    }

    public static void closeWebDriver() {
        container.closeDriver();
    }
}
