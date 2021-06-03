package ru.instamart.reforged.admin.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.page.Page;
import ru.instamart.ui.manager.AppManager;

public interface AdminPage extends Page {

    default void goToPage() {
        AppManager.getWebDriver().get(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + pageUrl());
    }
}
