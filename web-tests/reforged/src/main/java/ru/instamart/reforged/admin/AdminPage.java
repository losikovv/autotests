package ru.instamart.reforged.admin;

import io.qameta.allure.Step;
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
        Kraken.jsAction().waitForDocumentReady();
    }

    default void goToPageOld(final String pageUrl) {
        Kraken.open(addBasicAuthToUrl(UiProperties.ADMIN_URL + pageUrl));
        Kraken.jsAction().waitForDocumentReady();
    }

    @Override
    @Step("Ожидание загрузки страницы")
    default void waitPageLoad() {
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().checkPendingRequests();
    }
}
