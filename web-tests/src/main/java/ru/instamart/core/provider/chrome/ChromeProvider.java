package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import kong.unirest.json.JSONObject;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static instamart.core.settings.Config.VIDEO;
import static instamart.core.settings.Config.VNC;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        final JSONObject jsonObject = new JSONObject();
        final ChromeOptions options = new ChromeOptions();
        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);
        options.setExperimentalOption("prefs", jsonObject);
        capabilities.setBrowserName("chrome");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//        capabilities.setVersion(version);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", VNC,
                "enableVideo", VIDEO
        ));
        createRemoteDriver(capabilities);
    }
}
