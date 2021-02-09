package instamart.core.provider.firefox;

import instamart.core.provider.AbstractBrowserProvider;
import instamart.core.util.ProcessUtils;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;

import java.util.Optional;

import static instamart.core.settings.Config.DO_CLEANUP_BEFORE_TEST_RUN;

public final class FirefoxLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        if (DO_CLEANUP_BEFORE_TEST_RUN) {
            ProcessUtils.cleanProcessByName(BrowserType.FIREFOX);
        }
        final FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        final FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        //This is fore resolution
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        //This for screenshot
        options.addArguments("--window-size").addArguments("1920,1080");

        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("webdriver.gecko.driver", "WebDriverMac/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "WebDriverLinux/geckodriver");
        }

        createLocalFirefoxDriver(Optional.of(options));
    }
}
