package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.util.ProcessUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.CHROME);
        }
        final ChromeOptions options = new ChromeOptions();
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", "WebDriverMac/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "WebDriverLinux/chromedriver");
            options.setHeadless(true);
        }
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        //options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());

//        createLocalChromeDriver(Optional.of(options));
            createLocalChromeDriver(Optional.of(capabilities));
    }
}
