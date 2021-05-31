package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.action.WaitAction;
import ru.instamart.ui.manager.AppManager;

public interface Page {

    String pageUrl();

    default void goToPage() {
        WaitAction.urlToBe(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
        JsAction.waitForDocumentReady();
    }
}
