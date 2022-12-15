package ru.instamart.reforged.core.provider;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriverException;
import ru.instamart.reforged.core.driver.DriverSession;
import ru.instamart.reforged.core.enums.Browser;
import ru.instamart.reforged.core.provider.chrome.ChromeLocalProvider;
import ru.instamart.reforged.core.provider.chrome.ChromeProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxLocalProvider;
import ru.instamart.reforged.core.provider.firefox.FirefoxProvider;

import static java.util.Objects.isNull;

@Slf4j
public final class BrowserFactory {

    public static DriverSession createDriverSession(final Browser browserType, final String version) {
        //System.setProperty("webdriver.http.factory", "jdk-http-client");
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
        log.debug("Try to create driver for browser={} with version{} in thread={}", browserType, version, Thread.currentThread().getId());

        if (isNull(provider.getDriver()) || isNull(provider.getDevTools())) {
            throw new WebDriverException(String.format("Driver=%s or devTools=%s is null", provider.getDriver(), provider.getDevTools()));
        }

        log.debug("Driver was created cdp session_id={} for thread_id={}", provider.getDevTools().getCdpSession(), Thread.currentThread().getId());
        return new DriverSession(provider.getDriver(), provider.getDevTools());
    }
}
