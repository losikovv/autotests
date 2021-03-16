package instamart.core.provider.chrome;

import instamart.core.provider.AbstractBrowserProvider;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static instamart.core.settings.Config.VIDEO;
import static instamart.core.settings.Config.VNC;

public final class ChromeProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//        capabilities.setVersion(version);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", VNC,
                "enableVideo", VIDEO
        ));
        createRemoteDriver(capabilities);
    }
}
