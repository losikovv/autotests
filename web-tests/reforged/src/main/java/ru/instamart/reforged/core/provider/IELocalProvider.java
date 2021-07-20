package ru.instamart.reforged.core.provider;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Optional;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        WebDriverManager.iedriver().setup();
        createLocalIEDriver(Optional.empty());
    }
}
