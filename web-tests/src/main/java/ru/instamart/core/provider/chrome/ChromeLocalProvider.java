package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            cleanProcessByName(BrowserType.CHROME);
        }
        final ChromeOptions options = new ChromeOptions();
        if ((System.getProperty("os.name")).contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", "WebDriverMac/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "WebDriverLinux/chromedriver");
        }
        options.addArguments("--headless");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        //options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());

        createLocalChromeDriver(Optional.of(options));
    }
}
