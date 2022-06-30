package ru.instamart.reforged.next.page;

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
        if (BrowserProperties.ENABLE_PROXY) {
            CdpHeaders.addHeader(Map.of("sbm-forward-feature-version-stf", UiProperties.HEADER_STF_FORWARD_TO));
        }
        Kraken.open(UiProperties.STF_URL + url);
    }
}
