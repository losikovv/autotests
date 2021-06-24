package ru.instamart.reforged.core.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.action.JsAction;

public interface Page extends PageCheck {

    String pageUrl();

    default void scrollUp() {
        JsAction.scrollToTheTop();
    }

    default void scrollDown() {
        JsAction.scrollToTheBottom();
    }

    default void confirmBrowserAlert() {
        Kraken.alertConfirm();
    }

    default void declineBrowserAlert() {
        Kraken.alertDismiss();
    }
}
