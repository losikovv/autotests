package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;

public class TestDataGenerator {

    /** Generate user data for testing needs */
    public static UserData generateUserData() {
        return new UserData("autotestuser-" + generateRandomSuffix() + "@example.com", "instamart", "Автотест Юзер");
    }

    /** Generate 8-characters length string */
    public static String generateRandomSuffix() {
        final int LENGTH = 8;
        String s = "123456789abcdefghijklmnopqrstuvwxyz";
        StringBuffer number = new StringBuffer();

        for (int i = 0; i < LENGTH; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return number.toString();
    }

}
