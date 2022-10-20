package ru.instamart.reforged.core.provider.firefox;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.config.WaitProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.time.Duration;

public class AbstractFirefoxProvider extends AbstractBrowserProvider {

    protected final FirefoxProfile firefoxProfile = new FirefoxProfile();
    protected final FirefoxOptions options = new FirefoxOptions();

    @Override
    public void createDriver(String version) {
        firefoxProfile.setPreference("geo.enabled", false);
        firefoxProfile.setPreference("geo.prompt.testing", false);
        firefoxProfile.setPreference("geo.prompt.testing.allow", false);

        options.setProfile(firefoxProfile);

        options.addArguments("--disable-geolocation");
        //This for screenshot
        options.addArguments("--window-size=1920,1080");

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        options.setHeadless(BrowserProperties.HEADLESS);
        options.setBrowserVersion(version);
        options.setAcceptInsecureCerts(BrowserProperties.IGNORE_SSL);
        options.addArguments("window-size=1920,1080");
        options.setPageLoadTimeout(Duration.ofSeconds(WaitProperties.MAX_PAGE_LOAD_TIMEOUT));
        options.setScriptTimeout(Duration.ofSeconds(WaitProperties.MAX_SCRIPT_LOAD_TIMEOUT));
    }
}
