package ru.instamart.reforged.core.page;

import ru.instamart.reforged.action.JsAction;

public interface Page extends PageCheck {

    String pageUrl();

    default void scrollUp() {
        JsAction.scrollToTheTop();
    }

    default void scrollDown() {
        JsAction.scrollToTheBottom();
    }
}
