package ru.instamart.reforged.core.util;

public final class StringUtil {

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(text.replaceAll("\\D+",""));
    }
}
