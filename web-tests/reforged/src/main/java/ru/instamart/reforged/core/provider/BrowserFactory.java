package ru.instamart.reforged.core.provider;

import org.openqa.selenium.WebDriver;
import ru.instamart.reforged.core.provider.chrome.ChromeLocalProvider;
import ru.instamart.reforged.core.provider.chrome.ChromeProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxLocalProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxProvider;

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

        return provider.getDriver();
    }
}
