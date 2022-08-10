package ru.instamart.reforged.active_directory;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface ActiveDirectoryPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.ACTIVE_DIRECTORY_URL + url);
    }
}
