package ru.instamart.reforged.admin;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface AdminPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + pageUrl());
        Kraken.jsAction().waitForDocumentReady();
    }
}
