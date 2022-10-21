package ru.instamart.reforged.business.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface BusinessPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(addBasicAuthToUrl(UiProperties.B2B_FRONT_URL + url));
    }
}
