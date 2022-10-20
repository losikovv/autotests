package ru.instamart.reforged.stf.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.cdp.CdpHeaders;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

import java.util.Map;

public interface StfPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(addBasicAuthToUrl(UiProperties.Env.FRONT_URL + url));
    }
}
