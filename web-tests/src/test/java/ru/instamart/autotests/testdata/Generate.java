package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;

import static ru.instamart.autotests.application.Config.companyName;
import static ru.instamart.autotests.application.Config.testUserEmailBase;

public class Generate {

    private static String symbolSetSwitcher(String symbolsSet) {
        switch (symbolsSet){
            case "integer" :
                return "123456789";
            case "string" :
                return "1234567890abcdefghijklmnopqrstuvwxyz";
            case "literal" :
                return "abcdefghijklmnopqrstuvwxyz";
            default:
                return "";
        }
    }

    /** Генератор строки с набором символов указанной длины */
    private static String buildString(String symbolsSet, int length) {
        if(length > 0) {
            String symbols = symbolSetSwitcher(symbolsSet);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(symbols.charAt(new Random().nextInt(symbols.length())));
            }
            return builder.toString();
        } else return "";
    }

    public interface random {

        static int integer(int length) {
            return Integer.parseInt(buildString("integer", length));
        }

        static String string(int length) {
            return buildString("string", length);
        }

        static String literalString(int length) {
            return buildString("literal", length);
        }
    }

    //TODO объединить методы testUserData, обернув один в другой
    /** Сгенерировать стандартные реквизиты тестового пользователя */
    public static UserData testUserData() {
        UserData testuser = new UserData(testEmail(8), companyName, companyName + " test");
        System.out.println("Сгенерированы реквизиты тестового юзера");
        System.out.println("Имя: " + testuser.getName());
        System.out.println("Логин: " + testuser.getLogin());
        System.out.println("Пароль: " + testuser.getPassword() + "\n");
        return testuser;
    }

    /** Сгенерировать реквизиты тестового ппользователя со значениями указанной длины */
    public static UserData testUserData(int reqsLength) {
        UserData testuser = new UserData(
                testEmail(reqsLength - testUserEmailBase.length() - 1),
                random.string(reqsLength),
                random.literalString(reqsLength));
        System.out.println("Test user requisites generated");
        System.out.println("name: " + testuser.getName());
        System.out.println("login: " + testuser.getLogin());
        System.out.println("password: " + testuser.getPassword() + "\n");
        return testuser;
    }

    /** Сгенерировать тестовый имейл с префиксом указанной длины */
    public static String testEmail(int prefixLength) {
        return random.literalString(prefixLength) + "-" + testUserEmailBase;
    }
}
