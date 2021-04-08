package ru.instamart.core.factory;

import ru.instamart.core.provider.AbstractBrowserProvider;
import ru.instamart.core.provider.IELocalProvider;
import ru.instamart.core.provider.SafariLocalProvider;
import ru.instamart.core.provider.chrome.ChromeLocalProvider;
import ru.instamart.core.provider.chrome.ChromeProvider;
import ru.instamart.core.provider.firefox.FirefoxLocalProvider;
import ru.instamart.core.provider.firefox.FirefoxProvider;
import org.openqa.selenium.WebDriver;

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
