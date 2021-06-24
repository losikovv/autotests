package ru.instamart.reforged.core.page;

import ru.instamart.reforged.core.action.WaitAction;

public interface PageCheck {

    default void checkPageEquals(final String expectedUrl) {
        WaitAction.urlEquals(expectedUrl);
    }

    default void checkPageContains(final String expectedUrl) {
        WaitAction.urlContains(expectedUrl);
    }
}
