package ru.instamart.reforged.stf.page;

import org.openqa.selenium.support.PageFactory;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.ui.manager.AppManager;

public interface Page {

    String pageUrl();

    default void goToPage() {
        AppManager.getWebDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + pageUrl());
    }
}
