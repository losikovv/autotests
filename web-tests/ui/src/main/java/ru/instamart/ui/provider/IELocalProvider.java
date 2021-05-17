package ru.instamart.ui.provider;

import java.util.Optional;

public final class IELocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        createLocalIEDriver(Optional.empty());
    }
}
