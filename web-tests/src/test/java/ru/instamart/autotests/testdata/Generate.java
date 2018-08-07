package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;



    // Генератор тестовых данных



public class Generate {

    /** Generate user data for testing needs */
    public static UserData testUserData() {
        final String prefix = randomString(8);
        final String base = "testuser@example.com";
        return new UserData(prefix + "-" + base, "instamart", "Тест Юзер");
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
