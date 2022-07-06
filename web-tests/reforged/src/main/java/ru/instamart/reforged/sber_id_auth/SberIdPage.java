package ru.instamart.reforged.sber_id_auth;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface SberIdPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.SBER_ID_URL + url);
    }
}
