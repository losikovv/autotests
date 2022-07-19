package ru.instamart.kraken.helper;

import lombok.NonNull;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDHelper {
    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
    private final static Pattern UUID_REGEX_PATTERN2 =
            Pattern.compile("/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}/");



    public static boolean isValidUUID(@NonNull final String str) {
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }

    public static String getFirstUUID(@NonNull final String str){
        Matcher matcher = UUID_REGEX_PATTERN2.matcher(str);
        while(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
