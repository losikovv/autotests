package ru.instamart.reforged.core.provider.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.instamart.kraken.util.FileUtils;
import ru.instamart.reforged.core.provider.AbstractBrowserProvider;

import java.util.Optional;

public final class ChromeLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        var options = new ChromeOptions();
        var jsonObject = new JSONObject();
        WebDriverManager.chromedriver().setup();

        jsonObject.put("profile.default_content_settings.geolocation", 2);
        jsonObject.put("profile.managed_default_content_settings.geolocation", 2);
        jsonObject.put("credentials_enable_service", false);
        jsonObject.put("profile.password_manager_enabled", false);

        //TODO: Добавить поддержку профилей в Moon
        /*options.addArguments("--allow-profiles-outside-user-dir");
        options.addArguments("--enable-profile-shortcut-manager");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-data-dir=" + FileUtils.getResourceDir("Selenium"));
        options.addArguments("--profile-directory=Profile");*/

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-geolocation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");

        options.setExperimentalOption("prefs", jsonObject);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setCapability("detach", true);
        options.setCapability(CapabilityType.LOGGING_PREFS, getLogPref());
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        createLocalChromeDriver(Optional.of(options));
    }
}
