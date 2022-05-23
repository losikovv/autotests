package ru.instamart.reforged.core.provider.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;
import ru.instamart.reforged.core.provider.BrowserProxy;

import java.util.Optional;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final var options = new ChromeOptions();
        final var jsonObject = new JSONObject();
        WebDriverManager.chromedriver().browserVersion(BrowserProperties.BROWSER_LOCAL_VERSION).setup();

        if (BrowserProperties.ENABLE_PROXY) {
            if (BrowserProperties.IGNORE_SSL) {
                options.setAcceptInsecureCerts(true);
            }
            options.addArguments("--proxy-server=" + BrowserProxy.INSTANCE.getLocalProxy());
        }

        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);
        jsonObject.put("credentials_enable_service", false);
        jsonObject.put("profile.password_manager_enabled", false);

        if (BrowserProperties.ENABLE_PROFILE) {
            options.addArguments("--user-data-dir=" + BrowserProperties.PROFILE_PATH);
            options.addArguments("--profile-directory=" + BrowserProperties.PROFILE_NAME);
        }

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");

        options.setExperimentalOption("prefs", jsonObject);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setCapability("detach", true);
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        createLocalChromeDriver(Optional.of(options));
    }
}
