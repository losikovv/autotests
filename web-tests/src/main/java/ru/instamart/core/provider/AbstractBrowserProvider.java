package instamart.core.provider;

import instamart.core.settings.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static instamart.core.settings.Config.BASIC_TIMEOUT;
import static instamart.core.settings.Config.FULL_SCREEN_MODE;

public abstract class AbstractBrowserProvider {

    private static final Logger log = LoggerFactory.getLogger(AbstractBrowserProvider.class);

    protected WebDriver driver;

    public abstract void createDriver(final String version);

    protected void createRemoteDriver(final DesiredCapabilities capabilities) {
        try {
            this.driver = new RemoteWebDriver(
                    URI.create(Config.REMOTE_URL).toURL(),
                    capabilities);
            ((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
            applyOptions();
        } catch (Exception e) {
            log.error("Protocol exception ", e);
        }
    }

    protected void createLocalChromeDriver(final Optional<DesiredCapabilities> capabilities) {
        this.driver = capabilities.map(ChromeDriver::new).orElseGet(ChromeDriver::new);
        applyOptions();
    }

    protected void createLocalSafariDriver(final Optional<SafariOptions> options) {
        this.driver = options.map(SafariDriver::new).orElseGet(SafariDriver::new);
        applyOptions();
    }

    protected void createLocalFirefoxDriver(final Optional<FirefoxOptions> options) {
        this.driver = options.map(FirefoxDriver::new).orElseGet(FirefoxDriver::new);
        applyOptions();
    }

    protected void createLocalIEDriver(final Optional<InternetExplorerOptions> options) {
        this.driver = options.map(InternetExplorerDriver::new).orElseGet(InternetExplorerDriver::new);
        applyOptions();
    }

    private void applyOptions() {
        if (FULL_SCREEN_MODE) {
            driver.manage().window().maximize();
        }
        if (BASIC_TIMEOUT > 0) {
            driver.manage().timeouts().implicitlyWait(BASIC_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    /**
     * Уровни логирования в браузере
     */
    protected LoggingPreferences getLogPref() {
        final LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);
        logs.enable(LogType.CLIENT, Level.OFF);
        logs.enable(LogType.DRIVER, Level.WARNING);
        logs.enable(LogType.PERFORMANCE, Level.INFO);
        logs.enable(LogType.SERVER, Level.ALL);

        return logs;
    }

    public WebDriver getWebDriver() {
        return driver;
    }
}
