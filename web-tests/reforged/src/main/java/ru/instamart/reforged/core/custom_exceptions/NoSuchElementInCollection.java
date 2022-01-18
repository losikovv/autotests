package ru.instamart.reforged.core.custom_exceptions;

public class NoSuchElementInCollection extends AssertionError {

    public NoSuchElementInCollection(int elementIndex) {
        super(String.format("Element with index '%d' not found in collection", elementIndex));
    }
}
