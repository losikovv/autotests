package ru.instamart.kraken.util;

import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+");
    private static final Pattern citiesPattern = Pattern.compile("\\(([^\\D+]+)\\)");

    private StringUtil() {
    }

    public static String failMessage(final String text) {
        return "\n> " + text;
    }

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }

    public static double stringToDouble(String stringToParse) {
        stringToParse = stringToParse.replace(",", ".").replace(" ", "");

        final var matcher = pattern.matcher(stringToParse);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }

        return 0.0d;
    }

    public static Long stringToLong(final String stringToParse) {
        return Long.parseLong(stringToParse.replaceAll("\\D+", ""));
    }

    public static String cutBasicAuthFromUrl(final String url) {
        return url.replace(EnvironmentProperties.HTTP_AUTH, "");
    }

    public static String getPhone(final String phone) {
        return getPhoneNumber(phone).substring(1);
    }

    public static String getPhoneNumber(final String phone) {
        return phone.replaceAll("[^0-9]", "");
    }

    public static String getSMSCode(final String phone) {
        if (isNull(phone) || phone.isEmpty()) {
            return CoreProperties.DEFAULT_SMS;
        }
        return phone.substring(phone.length() - 6);
    }

    public static String getLastLine(final String text) {
        return text.replaceAll("^.+\\n", "");
    }

    /**
     *Парсит строку типа "Екатеринбург (17)" в (Integer)17, или "тест-375756123 (13)" в (Integer)13
     */
    public static int parseNumberCitiesFromString(final String text) {
        Matcher matcher = citiesPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else return 0;
    }
}
