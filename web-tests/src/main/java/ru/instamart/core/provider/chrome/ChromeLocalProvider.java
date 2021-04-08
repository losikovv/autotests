package ru.instamart.core.provider.chrome;

import org.json.simple.JSONObject;
import ru.instamart.core.provider.AbstractBrowserProvider;
import ru.instamart.core.util.ProcessUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Optional;

import static ru.instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.CHROME);
        }
        final ChromeOptions options = new ChromeOptions();
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        final JSONObject jsonObject = new JSONObject();
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
        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);
        options.setExperimentalOption("prefs", jsonObject);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        options.setCapability("detach", true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        //options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());

//        createLocalChromeDriver(Optional.of(options));
            createLocalChromeDriver(Optional.of(capabilities));
    }
}
