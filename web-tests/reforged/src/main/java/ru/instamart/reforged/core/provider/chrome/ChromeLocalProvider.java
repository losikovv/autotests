package ru.instamart.reforged.core.provider.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Optional;

public final class ChromeLocalProvider extends AbstractChromeProvider {

    @Override
    public void createDriver(final String version) {
        WebDriverManager.chromedriver().setup();
        super.createDriver(version);

        createLocalChromeDriver(Optional.of(options));
    }
}
