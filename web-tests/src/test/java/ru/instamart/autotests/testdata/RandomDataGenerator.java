package ru.instamart.autotests.testdata;

import java.util.Random;

public class RandomDataGenerator {

    /** Generate 8-characters length string*/

    public static String randomSuffix() {
        final int LENGTH = 8;
        String s = "123456789abcdefghijklmnopqrstuvwxyz";
        StringBuffer number = new StringBuffer();

        for (int i = 0; i < LENGTH; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return number.toString();
    }

}
