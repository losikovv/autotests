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
            case "chrome_local":
                provider = new ChromeLocalProvider();
                break;
            case "firefox_local":
                provider = new FirefoxLocalProvider();
                break;
            case "safari_local":
                provider = new SafariLocalProvider();
                break;
            case "ie_local":
                provider = new IELocalProvider();
                break;
            case "firefox":
                provider = new FirefoxProvider();
                break;
            case "chrome":
            default:
                provider = new ChromeProvider();
                break;
        }
        provider.createDriver(version);

        return provider.getWebDriver();
    }
}
