package instamart.core.factory;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.provider.IELocalProvider;
import instamart.core.provider.SafariLocalProvider;
import instamart.core.provider.chrome.ChromeLocalProvider;
import instamart.core.provider.chrome.ChromeProvider;
import instamart.core.provider.firefox.FirefoxLocalProvider;
import instamart.core.provider.firefox.FirefoxProvider;
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
