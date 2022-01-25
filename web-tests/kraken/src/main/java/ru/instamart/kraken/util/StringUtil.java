package ru.instamart.kraken.util;

import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+");

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

    /* Получает на вход массив List<String> {"Москва (17)","Санкт-Петербург (3)","Екатеринбург (7)"},
        а на выход отдает числа из скобок массивом Integer {17, 3, 7} */
    public static Integer[] returnNumbersOfCitiesInTableArray(List<String> cities) {
        Integer[] numArray = new Integer[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            int startIndex = cities.get(i).indexOf("(");
            int endIndex = cities.get(i).indexOf(")");
            String subs = cities.get(i).substring(startIndex + 1, endIndex);
            numArray[i] = Integer.parseInt(subs);
        }
        return numArray;
    }

    private StringUtil() {
    }
}
