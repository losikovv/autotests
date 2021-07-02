package ru.instamart.kraken.util;

public final class StringUtil {

    public static String failMessage(final String text) {
        return "\n> " + text;
    }

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(text.replaceAll("\\D+",""));
    }

    private StringUtil() {
    }
}
