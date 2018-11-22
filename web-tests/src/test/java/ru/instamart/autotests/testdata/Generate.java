package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;



    // Генератор тестовых данных



public class Generate {

    /** Сгенерировать int указанной длины */
    public static int randomInt(int length) {
        String s = "123456789";
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return Integer.parseInt(number.toString());
    }

    /** Сгенерировать строку указанной длины */
    public static String randomString(int length) {
        String s = "1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return number.toString();
    }

    /** Сгенерировать реквизиты тестового ппользователя */
    public static UserData testUserData() {
        final String prefix = randomString(8);
        final String base = "testuser@example.com";
        return new UserData(prefix + "_" + base, "instamart", "Тест Юзер");
    }

    //TODO
    /** Сгенерировать случайный адрес доставки в указанном городе */
    public static String randomAddress(String city) {
        return "TODO";
    }
}
