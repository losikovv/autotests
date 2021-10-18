package ru.instamart.reforged.core.provider;

import org.openqa.selenium.WebDriver;
import ru.instamart.reforged.core.enums.Browser;
import ru.instamart.reforged.core.provider.chrome.ChromeLocalProvider;
import ru.instamart.reforged.core.provider.chrome.ChromeProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxLocalProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxProvider;

public final class BrowserFactory {

    public static WebDriver createBrowserInstance(final Browser browserType, final String version) {
        AbstractBrowserProvider provider;
        switch (browserType) {
            case CHROME_REMOTE:
                provider = new ChromeProvider();
                break;
            case FIREFOX_REMOTE:
                provider = new FirefoxProvider();
                break;
            case FIREFOX:
                provider = new FirefoxLocalProvider();
                break;
            case SAFARI:
                provider = new SafariLocalProvider();
                break;
            case IE:
                provider = new IELocalProvider();
                break;
            case CHROME:
            default:
                provider = new ChromeLocalProvider();
                break;
        }
        provider.createDriver(version);

        return provider.getDriver();
    }
}
