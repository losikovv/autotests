package ru.instamart.ui.provider.chrome;

import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.instamart.ui.provider.AbstractBrowserProvider;

import java.util.Map;

import static ru.instamart.core.settings.Config.VIDEO;
import static ru.instamart.core.settings.Config.VNC;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        var capabilities = new DesiredCapabilities();
        var jsonObject = new JSONObject();
        var options = new ChromeOptions();

        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);

        options.setExperimentalOption("prefs", jsonObject);
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        capabilities.setBrowserName("chrome");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        capabilities.setVersion(version);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", VNC,
                "enableVideo", VIDEO
        ));

        createRemoteDriver(capabilities);
    }
}
