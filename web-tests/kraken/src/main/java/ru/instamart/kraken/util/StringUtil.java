package ru.instamart.kraken.util;

import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+");

    private static final Pattern citiesPattern = Pattern.compile("\\(([^\\D+]+)\\)");

    public static String failMessage(final String text) {
        return "\n> " + text;
    }

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(text.replaceAll("\\D+",""));
    }

    public static double stringToDoubleParse(String stringToParse) {
        stringToParse = stringToParse.replace(",",".").replace(" ", "");

        Matcher matcher = pattern.matcher(stringToParse);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group());
        }

        return Double.parseDouble(sb.toString());
    }

    public static Long stringToLongParse(String stringToParse) {
        return Long.parseLong(stringToParse.replaceAll("\\D+",""));
    }

    public static String cutBasicAuthFromUrl(final String url) {
        return url.replace(EnvironmentProperties.HTTP_AUTH, "");
    }

    public static String getPhone(final String phone) {
        return phone.replaceAll("[^0-9]", "").substring(1);
    }

    public static String getSMSCode(final String phone) {
        if (isNull(phone) || phone.isEmpty()) {
            return CoreProperties.DEFAULT_SMS;
        }
        return phone.substring(phone.length() - 6);
    }

    public static Integer parseNumberCitiesFromString(final String text) {
        Matcher matcher = citiesPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }else return null;
    }

    private StringUtil() {
    }
}
