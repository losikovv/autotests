package ru.instamart.reforged.admin;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface AdminPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
    }
}
