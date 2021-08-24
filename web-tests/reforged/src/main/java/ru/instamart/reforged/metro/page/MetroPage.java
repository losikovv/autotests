package ru.instamart.reforged.metro.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface MetroPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
    }
}
