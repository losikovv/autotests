package ru.instamart.ui.factory;

import org.openqa.selenium.WebDriver;
import ru.instamart.ui.provider.AbstractBrowserProvider;
import ru.instamart.ui.provider.IELocalProvider;
import ru.instamart.ui.provider.SafariLocalProvider;
import ru.instamart.ui.provider.chrome.ChromeLocalProvider;
import ru.instamart.ui.provider.chrome.ChromeProvider;
import ru.instamart.ui.provider.firefox.FirefoxLocalProvider;
import ru.instamart.ui.provider.firefox.FirefoxProvider;

public final class BrowserFactory {

    public static WebDriver createBrowserInstance(final String browserType, final String version) {
        AbstractBrowserProvider provider;
        switch (browserType) {
            case "firefox":
                provider = new FirefoxLocalProvider();
                break;
            case "safari":
                provider = new SafariLocalProvider();
                break;
            case "ie":
                provider = new IELocalProvider();
                break;
            case "firefox_remote":
                provider = new FirefoxProvider();
                break;
            case "chrome_remote":
                provider = new ChromeProvider();
                break;
            case "chrome":
            default:
                provider = new ChromeLocalProvider();
                break;
        }
        provider.createDriver(version);

        return provider.getWebDriver();
    }
}
