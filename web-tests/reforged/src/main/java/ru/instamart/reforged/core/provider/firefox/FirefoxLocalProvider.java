package ru.instamart.reforged.core.provider.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Optional;

public final class FirefoxLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        WebDriverManager.firefoxdriver().setup();
        final FirefoxBinary firefoxBinary = new FirefoxBinary();
//        firefoxBinary.addCommandLineOptions("--headless");
        final FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        //This is fore resolution
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.addArguments("--disable-geolocation");
        //This for screenshot
        options.addArguments("--window-size").addArguments("1920,1080");
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());

        createLocalFirefoxDriver(Optional.of(options));
    }
}
