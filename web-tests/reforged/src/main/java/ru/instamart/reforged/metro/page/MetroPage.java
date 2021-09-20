package ru.instamart.reforged.metro.page;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface MetroPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
    }
}
