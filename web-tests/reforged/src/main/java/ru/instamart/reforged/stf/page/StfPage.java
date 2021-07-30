package ru.instamart.reforged.stf.page;

import org.openqa.selenium.Cookie;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface StfPage extends Page {

    Cookie cookieAlert = new Cookie("cookies_consented",
            "yes",
            "sbermarket.tech",
            "/",
            null);

    default void goToPage() {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().jQueryReady();
    }
}
