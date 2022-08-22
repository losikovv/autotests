package ru.instamart.reforged.core.provider;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v103.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.provider.chrome.ChromeDriverExtension;

import java.net.URI;
import java.util.Optional;

@Slf4j
public abstract class AbstractBrowserProvider {

    @Getter
    protected WebDriver driver;
    @Getter
    protected DevTools devTools;

    public abstract void createDriver(final String version);

    protected void createRemoteDriver(final Capabilities capabilities) {
        try {
            this.driver = RemoteWebDriver.builder()
                    .address(URI.create(BrowserProperties.REMOTE_URL).toURL())
                    .augmentUsing(new Augmenter())
                    .oneOf(capabilities)
                    .build();
            applyOptions();
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        } catch (Exception e) {
            log.error("Protocol exception ", e);
        }
    }

    protected void createLocalChromeDriver(final Optional<ChromeOptions> capabilities) {
        this.driver = capabilities.map(ChromeDriverExtension::new).orElseGet(ChromeDriverExtension::new);
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
        initDevTools();
    }

    private void initDevTools() {
        this.devTools = ((HasDevTools) driver).getDevTools();
        this.devTools.createSession();
        this.devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    }
}
