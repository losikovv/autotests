package ru.instamart.reforged.core.page;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.ui.manager.AppManager;

public interface Page {

    String pageUrl();

    default void scrollUp() {
        JsAction.scrollToTheTop();
    }

    default void scrollDown() {
        JsAction.scrollToTheBottom();
    }
}
