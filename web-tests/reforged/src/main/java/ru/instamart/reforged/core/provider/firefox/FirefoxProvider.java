package ru.instamart.reforged.core.provider.firefox;

import org.openqa.selenium.remote.DesiredCapabilities;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Map;

import static ru.instamart.kraken.setting.Config.VIDEO;
import static ru.instamart.kraken.setting.Config.VNC;

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
