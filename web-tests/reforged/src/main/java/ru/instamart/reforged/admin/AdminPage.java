package ru.instamart.reforged.admin;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface AdminPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPageOld() {
        goToPageOld(pageUrl());
    }

    default void goToPage(final String pageUrl) {
        Kraken.open(addBasicAuthToUrl(UiProperties.ADMIN_URL + "spa/" + pageUrl));
        waitPageLoad();
    }

    default void goToPageOld(final String pageUrl) {
        Kraken.open(addBasicAuthToUrl(UiProperties.ADMIN_URL + pageUrl));
        waitPageLoad();
    }
}
