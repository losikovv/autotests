package ru.instamart.kraken.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderAmountHelper {

    public static Double getOrderAmountParsed(String nonParsedAmount) {
        nonParsedAmount = nonParsedAmount.replace(",",".");
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(nonParsedAmount);
        StringBuilder sb = new StringBuilder();
        while (matcher.find())
        {
            sb.append(matcher.group());
        }

        return Double.parseDouble(sb.toString());
    }
}
