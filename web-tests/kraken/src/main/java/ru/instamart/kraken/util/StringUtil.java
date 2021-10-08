package ru.instamart.kraken.util;

import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+");

    public static String failMessage(final String text) {
        return "\n> " + text;
    }

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(text.replaceAll("\\D+",""));
    }

    public static double orderAmountParse(String nonParsedAmount) {
        nonParsedAmount = nonParsedAmount.replace(",",".").replace(" ", "");

        Matcher matcher = pattern.matcher(nonParsedAmount);
        StringBuilder sb = new StringBuilder();
        while (matcher.find())
        {
            sb.append(matcher.group());
        }

        return Double.parseDouble(sb.toString());
    }

    public static String cutBasicAuthFromUrl(String url) {
        return url.replace(EnvironmentProperties.HTTP_AUTH, "");
    }

    public static String getPhone(final String phone) {
        return phone.replaceAll("[^0-9]", "").substring(1);
    }

    private StringUtil() {
    }
}
