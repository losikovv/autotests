package ru.instamart.reforged.core.provider.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Optional;

public final class FirefoxLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        WebDriverManager.firefoxdriver().setup();
        var fireFoxProfile = new FirefoxProfile();
        fireFoxProfile.setPreference("geo.enabled", false);
        fireFoxProfile.setPreference("geo.prompt.testing", false);
        fireFoxProfile.setPreference("geo.prompt.testing.allow", false);

        var options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setCapability(FirefoxDriver.PROFILE, fireFoxProfile);
        //This is fore resolution
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.addArguments("--disable-geolocation");
        //This for screenshot
        options.addArguments("--window-size=1920,1080");

        createLocalFirefoxDriver(Optional.of(options));
    }
}
