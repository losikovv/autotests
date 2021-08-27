package ru.instamart.reforged.okey.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface OkeyPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
    }
}
