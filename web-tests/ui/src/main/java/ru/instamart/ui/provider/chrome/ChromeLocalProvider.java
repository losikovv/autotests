package ru.instamart.ui.provider.chrome;

import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.kraken.util.FileUtils;
import ru.instamart.ui.provider.AbstractBrowserProvider;

import java.util.Optional;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        var options = new ChromeOptions();
        var jsonObject = new JSONObject();
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", FileUtils.getResourceDir("WebDriverMac/chromedriver"));
        } else {
            System.setProperty("webdriver.chrome.driver", FileUtils.getResourceDir("WebDriverLinux/chromedriver"));
            options.setHeadless(true);
        }

        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        options.setExperimentalOption("prefs", jsonObject);
        options.setCapability("detach", true);
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); //TODO: Проверить с eager
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        createLocalChromeDriver(Optional.of(options));
    }
}
