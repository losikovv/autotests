package ru.instamart.reforged.selgros.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface SelgrosPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.SELGROS_URL + url);
    }
}
