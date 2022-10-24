package ru.instamart.reforged.core.provider.chrome;

import org.testng.Reporter;
import ru.instamart.reforged.core.config.BrowserProperties;

import java.util.Map;

public final class ChromeProvider extends AbstractChromeProvider {

    @Override
    public void createDriver(final String version) {
        super.createDriver(version);
        options.setCapability("moon:options", Map.<String, Object>of(
                "name", Reporter.getCurrentTestResult().getName(),
                "enableVideo", BrowserProperties.VIDEO,
                "sessionTimeout", "5m",
                "screenResolution", "1920x1080x24"
        ));

        createRemoteDriver(options);
    }
}
