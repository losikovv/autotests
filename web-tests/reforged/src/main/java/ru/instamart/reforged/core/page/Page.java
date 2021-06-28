package ru.instamart.reforged.core.page;

import ru.instamart.reforged.core.Kraken;

public interface Page extends PageCheck {

    String pageUrl();

    default void scrollUp() {
        Kraken.jsAction().scrollToTheTop();
    }

    default void scrollDown() {
        Kraken.jsAction().scrollToTheBottom();
    }

    default void confirmBrowserAlert() {
        Kraken.alertConfirm();
    }

    default void declineBrowserAlert() {
        Kraken.alertDismiss();
    }
}
