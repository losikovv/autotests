package ru.instamart.reforged.core.provider.firefox;

import org.openqa.selenium.remote.DesiredCapabilities;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Map;

public final class FirefoxProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        //TODO: Firefox не работает с версией, если "default": "latest",
        //capabilities.setVersion(version);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", BrowserProperties.VNC,
                "enableVideo", BrowserProperties.VIDEO
        ));
        createRemoteDriver(capabilities);
    }
}
