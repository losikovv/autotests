package ru.instamart.reforged.sber_id_auth;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface SberIdPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(EnvironmentProperties.Env.FULL_SBER_ID_URL + url);
    }
}
