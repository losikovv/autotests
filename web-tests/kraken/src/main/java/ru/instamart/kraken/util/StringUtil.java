package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Slf4j
public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\dd+");
    private static final Pattern citiesPattern = Pattern.compile("\\(([^\\D+]+)\\)");
    private static final String PHONE_PATTERN = "(\\d{3})(\\d{3})(\\d{2})(\\d+)";
    private static final String EXTRA_ZERO_PATTERN = "0(\\d:)";
    private static final String LAST_CATALOG_FROM_PATH = "^.+/";
    private static final String NOT_SLOTS_IN_INTERVAL = "^.+:\\d{2} | \\D.+";
    private static final String AVAILABLE_SLOT_COUNT = "\\/.+";
    private static final String TOTAL_SLOT_COUNT = "^.+\\/";
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)");
    private static final String STRING_PATTERN = "([a-z])([A-Z]+)";

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

        final var matcher = DOUBLE_PATTERN.matcher(stringToParse);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        } else return 0.00d;
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
     * Парсит строку типа "9000000000" в "+7 (900) 000 00 00" для проверки номера в новом чекауте
     */
    public static String convertDigitsStringToPhoneNumberWithBracketsNoDash(final String text) {
        return text.replaceFirst(PHONE_PATTERN, "+7 ($1) $2 $3 $4");
    }

    /**
     * Парсит строку типа "09:00-10:00" в "9:00-10:00" для сравнения времени доставки
     */
    public static String cutExtraZerosFromDate(String hours) {
        return hours.replaceAll(EXTRA_ZERO_PATTERN, "$1");
    }

    /**
     * Вырезает из URL последний каталог пути
     */
    public static String getLastCatalogFromPath(String url) {
        return url.replaceAll(LAST_CATALOG_FROM_PATH, "");
    }

    /**
     * Заменяем дефис - знак отличается на разных страницах и в данных, полученных из API
     */
    public static String hyphenReplaceAndCutExtraZero(String stringToReplace) {
        return cutExtraZerosFromDate(stringToReplace.replaceAll("–", "-"));
    }

    /**
     * Получаем временной интервал из полного описания слота доставки
     */
    public static String getTimeFromDeliveryText(final String deliveryText) {
        return deliveryText.replaceAll(" .+", "");
    }

    /**
     * Получаем информацию о слотах из полного описания интервала доставки
     */
    public static String getSlotsFromDeliveryText(final String deliveryText) {
        return deliveryText.replaceAll(NOT_SLOTS_IN_INTERVAL, "");
    }

    /**
     * Получаем информацию о доступных слотах
     */
    public static String getAvailableSlotCount(final String slotsInfo) {
        return slotsInfo.replaceAll(AVAILABLE_SLOT_COUNT, "");
    }

    /**
     * Получаем информацию о доступных слотах
     */
    public static String getTotalSlotCount(final String slotsInfo) {
        return slotsInfo.replaceAll(TOTAL_SLOT_COUNT, "");
    }

    public static String getSMSCode(final String phone) {
        if (isNull(phone) || phone.isEmpty()) {
            return CoreProperties.DEFAULT_UI_SMS;
        }
        return phone.substring(phone.length() - 6);
    }

    public static String camelToSnake(String str) {
        String replacement = "$1_$2";
        str = str.replaceAll(STRING_PATTERN, replacement).toLowerCase();
        return str;
    }

    /**
     * Получаем 'Сегодня'/'Завтра' или дату из строки вида 'Сегодня, 1 января'/'Завтра, 2 января'/'Пн, 3 января'
     */
    public static String getTodayTomorrowOrDate(final String dayText) {
        var delimiter = ',';
        return dayText.indexOf(delimiter) < 6
                ? dayText.substring(dayText.indexOf(delimiter) + 2).replaceAll("\\.", "")
                : dayText.substring(0, dayText.indexOf(delimiter));
    }
}
