package ru.instamart.reforged.core.service;

import org.openqa.selenium.WebDriver;

public final class KrakenDriver {

    private static final WebDriverContainer container = new WebDriverContainer();

    public static WebDriver getWebDriver() {
        return container.createOrGetDriver();
    }

    public static void closeWebDriver() {
        container.closeDriver();
    }
}
