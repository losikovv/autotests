package ru.instamart.ui.provider.firefox;

import ru.instamart.ui.provider.AbstractBrowserProvider;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static ru.instamart.core.settings.Config.VIDEO;
import static ru.instamart.core.settings.Config.VNC;

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
