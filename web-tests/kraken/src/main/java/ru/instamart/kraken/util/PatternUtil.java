package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public final class PatternUtil {

    private static final String stringWithRussianLetters = "[а-яА-Я].+";
    private static final String productWeight = "(^\\d+|^\\d+.\\d+) (г|кг|мл|л|шт)( x \\d+)?";
    private static final String productQuantity = "(^\\d+) шт";
    private static final String costWithThousandSeparator = "(^\\d+,\\d\\d)|(^\\d+ \\d\\d\\d,\\d\\d) ₽";

    private PatternUtil() {
    }

    public static Pattern rusString() {
        return Pattern.compile(stringWithRussianLetters);
    }

    public static Pattern productWeight() {
        return Pattern.compile(productWeight);
    }

    public static Pattern productQuantity() {
        return Pattern.compile(productQuantity);
    }

    public static Pattern costWithThousandSeparator() {
        return Pattern.compile(costWithThousandSeparator);
    }
}
