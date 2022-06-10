package ru.instamart.reforged.core.provider.chrome;

import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Map;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final var jsonObject = new JSONObject();
        final var options = new ChromeOptions();

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
        options.addArguments("--disable-notifications");

        options.setExperimentalOption("prefs", jsonObject);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        options.setCapability("browserVersion", version);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        options.setCapability("moon:options", Map.<String, Object>of(
                "enableVideo", BrowserProperties.VIDEO,
                "sessionTimeout", "5m",
                "screenResolution", "1920x1080x24"
        ));

        createRemoteDriver(options);
    }
}
