package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.util.ProcessUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.CHROME);
        }
        final ChromeOptions options = new ChromeOptions();
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", "WebDriverMac/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "WebDriverLinux/chromedriver");
            options.addArguments("--headless");
        }
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        //options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());

        createLocalChromeDriver(Optional.of(options));
    }
}
