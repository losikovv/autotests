package ru.instamart.reforged.core.exceptions;

public final class NoSuchElementInCollection extends AssertionError {

    public NoSuchElementInCollection(final int elementIndex) {
        super(String.format("Element with index '%d' not found in collection", elementIndex));
    }
}
