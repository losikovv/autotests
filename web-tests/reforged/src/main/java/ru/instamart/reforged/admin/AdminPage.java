package ru.instamart.reforged.admin;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface AdminPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String pageUrl) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + pageUrl);
        Kraken.jsAction().waitForDocumentReady();
    }

    @Override
    @Step("Ожидание загрузки страницы")
    default void waitPageLoad() {
        Kraken.jsAction().waitForDocumentReady();
        Kraken.jsAction().checkPendingRequests();
    }
}
