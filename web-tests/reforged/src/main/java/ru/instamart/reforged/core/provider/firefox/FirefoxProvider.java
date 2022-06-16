package ru.instamart.reforged.core.provider.firefox;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Map;

public final class FirefoxProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        var fireFoxProfile = new FirefoxProfile();
        fireFoxProfile.setPreference("geo.enabled", false);
        fireFoxProfile.setPreference("geo.prompt.testing", false);
        fireFoxProfile.setPreference("geo.prompt.testing.allow", false);

        var options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        //This is fore resolution
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.addArguments("--disable-geolocation");
        //This for screenshot
        options.addArguments("--window-size=1920,1080");

        var capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setVersion(version);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        capabilities.setCapability("sessionTimeout", "5m");
        capabilities.setCapability("moon:options", Map.<String, Object>of(
                "enableVideo", BrowserProperties.VIDEO,
                "sessionTimeout", "5m",
                "screenResolution", "1920x1080x24"
        ));
        createRemoteDriver(capabilities);
    }
}
