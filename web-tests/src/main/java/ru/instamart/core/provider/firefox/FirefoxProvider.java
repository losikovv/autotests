package instamart.core.provider.firefox;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.settings.Config;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static instamart.core.settings.Config.VIDEO;
import static instamart.core.settings.Config.VNC;

public final class FirefoxProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        //TODO: Firefox не работает с версией, если "default": "latest",
        //capabilities.setVersion(version);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", VNC,
                "enableVideo", VIDEO
        ));
        createRemoteDriver(capabilities);
    }
}
