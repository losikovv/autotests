package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.settings.Config;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static instamart.core.settings.Config.VIDEO;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(Config.DEFAULT_BROWSER);
        capabilities.setVersion(Config.BROWSER_VERSION);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", VIDEO
        ));
        createRemoteDriver(capabilities);
    }
}
