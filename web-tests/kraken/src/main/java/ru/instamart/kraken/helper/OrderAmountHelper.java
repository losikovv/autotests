package ru.instamart.kraken.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class OrderAmountHelper {

    private OrderAmountHelper() {
        //not called
    }

    private static Double orderAmountParse(String nonParsedAmount) {
        nonParsedAmount = nonParsedAmount.replace(",",".").replace(" ", "");
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(nonParsedAmount);
        StringBuilder sb = new StringBuilder();
        while (matcher.find())
        {
            sb.append(matcher.group());
        }

        return Double.parseDouble(sb.toString());
    }

    public static Double getOrderAmountParsed(String nonParsedAmount) {
        return orderAmountParse(nonParsedAmount);
    }
}
