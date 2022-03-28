package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Slf4j
public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+");
    private static final Pattern citiesPattern = Pattern.compile("\\(([^\\D+]+)\\)");
    private static final String PHONE_PATTERN = "(\\d{3})(\\d{3})(\\d{2})(\\d+)";
    private static final String EXTRA_ZERO_PATTERN = "^(0\\d\\:\\d\\d)";
    private static final String HOUR_PATTERN = "^(\\d\\:\\d\\d)";

    private StringUtil() {
    }

    public static String failMessage(final String text) {
        return "\n> " + text;
    }

    public static int extractNumberFromString(final String text) {
        return Integer.parseInt(parseNumericFromString(text));
    }

    public static String parseNumericFromString(final String text) {
        return text.replaceAll("\\D+", "");
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

    public static String addBasicAuthToUrl(final String url) {
        return url.replace("://", "://" + EnvironmentProperties.HTTP_AUTH);
    }

    public static String getPhone(final String phone) {
        return getPhoneNumber(phone).substring(1);
    }

    public static String getPhoneNumber(final String phone) {
        return phone.replaceAll("[^0-9]", "");
    }

    public static String getLastLine(final String text) {
        return text.replaceAll("^.+\\n", "");
    }

    /**
     * Парсит строку типа "Екатеринбург (17)" в (Integer)17, или "тест-375756123 (13)" в (Integer)13
     */
    public static int parseNumberCitiesFromString(final String text) {
        final var matcher = citiesPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else return 0;
    }

    public static String cutLastFourSymbolsFromString(final String text) {
        return text.substring(text.length() - 4);
    }

    /**
     * Парсит строку типа "9000000000" в "+7 900 000-00-00" для проверки номера на странице заказов пользователя
     */
    public static String convertDigitsStringToPhoneNumber(final String text) {
        return text.replaceFirst(PHONE_PATTERN, "+7 $1 $2-$3-$4");
    }

    /**
     * Парсит строку типа "9000000000" в "+7 (900) 000-00-00" для проверки номера на странице корзины
     */
    public static String convertDigitsStringToPhoneNumberWithBrackets(final String text) {
        return text.replaceFirst(PHONE_PATTERN, "+7 ($1) $2-$3-$4");
    }

    /**
     * Парсит строку типа "09:00-10:00" в "9:00-10:00" для сравнения времени доставки
     */
    public static String cutExtraZerosFromDate(final String hours) {
        return hours.replaceAll(EXTRA_ZERO_PATTERN, HOUR_PATTERN);
    }
}
