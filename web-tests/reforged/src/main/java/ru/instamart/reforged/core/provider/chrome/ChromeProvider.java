package ru.instamart.reforged.core.provider.chrome;

import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Map;

import static ru.instamart.kraken.setting.Config.VIDEO;
import static ru.instamart.kraken.setting.Config.VNC;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        var capabilities = new DesiredCapabilities();
        var jsonObject = new JSONObject();
        var options = new ChromeOptions();

        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);
        jsonObject.put("credentials_enable_service", false);
        jsonObject.put("profile.password_manager_enabled", false);

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");

        options.setExperimentalOption("prefs", jsonObject);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        capabilities.setBrowserName("chrome");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability("sessionTimeout", "5m");
//        capabilities.setVersion(version);
        capabilities.setCapability("moon:options", Map.<String, Object>of(
                "enableVNC", VNC,
                "enableVideo", VIDEO,
                "sessionTimeout", "5m",
                "screenResolution", "1920x1080x24"
        ));

        createRemoteDriver(capabilities);
    }
}
