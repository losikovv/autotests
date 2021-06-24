package ru.instamart.reforged.core.provider;

import java.util.Optional;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        createLocalIEDriver(Optional.empty());
    }
}
