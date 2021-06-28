package ru.instamart.reforged.core.page;

import ru.instamart.reforged.core.Kraken;

public interface PageCheck {

    default void checkPageEquals(final String expectedUrl) {
        Kraken.waitAction().urlEquals(expectedUrl);
    }

    default void checkPageContains(final String expectedUrl) {
        Kraken.waitAction().urlContains(expectedUrl);
    }
}
