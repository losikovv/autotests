package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.core.page.Page;
import ru.instamart.ui.manager.AppManager;

public interface StfPage extends Page {

    default void goToPage() {
        AppManager.getWebDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
        JsAction.waitForDocumentReady();
        JsAction.reactReady();
    }
}
