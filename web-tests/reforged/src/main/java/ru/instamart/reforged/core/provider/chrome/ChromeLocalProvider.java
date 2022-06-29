package ru.instamart.reforged.core.provider.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.config.WaitProperties;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        final var options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();

        if (BrowserProperties.ENABLE_PROFILE) {
            options.addArguments("--user-data-dir=" + BrowserProperties.PROFILE_PATH);
            options.addArguments("--profile-directory=" + BrowserProperties.PROFILE_NAME);
        }

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        options.setExperimentalOption("prefs", Map.of(
                "profile.default_content_settings.geolocation", 2,
                "profile.managed_default_content_settings.geolocation", 2,
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        options.setCapability("detach", true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        options.setHeadless(BrowserProperties.HEADLESS);
        options.setAcceptInsecureCerts(BrowserProperties.IGNORE_SSL);
        options.addArguments("window-size=1920,1080");
        options.setPageLoadTimeout(Duration.ofSeconds(WaitProperties.MAX_PAGE_LOAD_TIMEOUT));
        options.setScriptTimeout(Duration.ofSeconds(WaitProperties.MAX_SCRIPT_LOAD_TIMEOUT));

        createLocalChromeDriver(Optional.of(options));
    }
}
