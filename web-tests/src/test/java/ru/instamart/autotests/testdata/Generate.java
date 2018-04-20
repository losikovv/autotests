package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;

public class Generate {

    /** Generate user data for testing needs */
    public static UserData autotestUserData() {
        return new UserData(randomString(8) + "-testuser@example.com", "instamart", "Тест Юзер");
    }

    /** Generate random string with given length */
    public static String randomString(int length) {
        String s = "123456789abcdefghijklmnopqrstuvwxyz";
        StringBuffer number = new StringBuffer();
        for (int i = 0; i < length; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return number.toString();
    }

}
