package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.page.Page;
import ru.instamart.ui.manager.AppManager;

public interface StfPage extends Page {

    default void goToPage() {
        //todo ожидать загрузку скриптов
        AppManager.getWebDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
    }
}
