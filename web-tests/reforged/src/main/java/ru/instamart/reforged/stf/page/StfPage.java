package ru.instamart.reforged.stf.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface StfPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(addBasicAuthToUrl(UiProperties.STF_URL + url));
    }
}
