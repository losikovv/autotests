package ru.instamart.reforged.core.provider;

import java.util.Optional;

public final class SafariLocalProvider extends AbstractBrowserProvider {

    @Override
    public void createDriver(final String version) {
        createLocalSafariDriver(Optional.empty());
    }
}
