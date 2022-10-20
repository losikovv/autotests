package ru.instamart.reforged.core.provider.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Optional;

public final class FirefoxLocalProvider extends AbstractFirefoxProvider {

    @Override
    public void createDriver(final String version) {
        WebDriverManager.firefoxdriver().setup();
        super.createDriver(version);

        createLocalFirefoxDriver(Optional.of(options));
    }
}
