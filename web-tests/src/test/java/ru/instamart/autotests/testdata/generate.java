package ru.instamart.autotests.testdata;

import ru.instamart.autotests.models.UserData;
import java.util.Random;
import static ru.instamart.autotests.application.Config.*;
import static ru.instamart.autotests.appmanager.ApplicationManager.testrunId;

public class generate {

    /**
     * Сгенерировать int c рандомным числом указанной длины
     */
    public static int integer(int length) {
        return Integer.parseInt(digitString(length));
    }

    /**
     * Сгенерировать рандомноую строку указанной длины
     */
    public static String string(int length) {
        return generate.string("string", length);
    }

    /**
     * Сгенерировать рандомноую буквенную строку указанной длины
     */
    public static String literalString(int length) { return generate.string("literal", length); }

    /**
     * Сгенерировать рандомноую цифровую строку указанной длины
     */
    public static String digitString(int length) { return generate.string("integer", length); }

    public static String testUserPhone(String userId) {
        return "777" + userId;
    }

    public static String testUserName(String role) {
        return testMark + "-" + role + " " + testrunId;
    }

    public static String testUserEmail(String role, String userId) {
        String domain;
        if(role.equals("admin")) { domain = companyDomain; } else {domain = testDomain;}
        return userId + "-" + testrunId + "-" + testMark + "@" + domain;
    }

    public static UserData testUserCredentials() {
        return testCredentials("user");
    }

    public static UserData testUserCredentials(int prefixLength) {
        return testCredentials("user", prefixLength);
    }

    public static UserData testCredentials(UserData role) {
        return testCredentials(role.getRole(), 0);
    }

    public static UserData testCredentials(String role) {
        return testCredentials(role, 0);
    }

    public static UserData testCredentials(String role, int prefixLength) {
        String testUserId = generate.userId();
        UserData testuser = new UserData(role, testUserEmail(role, testUserId), testUserPhone(testUserId), companyName, testUserName(role));
        if (prefixLength > 0) {
            String prefix = literalString(prefixLength);
            testuser.setEmail(prefix + "-" + testuser.getEmail());
            testuser.setPassword(prefix + "-" + testuser.getPassword());
            testuser.setName(prefix + "-" + testuser.getName());
        }
        System.out.println("Сгенерированы тестовые реквизиты для роли " + role);
        System.out.println("Телефон: " + testuser.getPhone());
        System.out.println("Email: " + testuser.getEmail());
        System.out.println("Пароль: " + testuser.getPassword());
        System.out.println("Имя: " + testuser.getName());
        System.out.println("\n");
        return testuser;
    }
    
    public static String testRunId() {
        return buildString("literal", 9).toUpperCase();
    }

    public static String userId() {
        return buildString("integer", 7);
    }

    public static String string(String symbols , int length) {
        return buildString("string", length);
    }

    private static String buildString(String symbolsSet, int length) {
        if (length > 0) {
            String symbols = symbolSetSwitcher(symbolsSet);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(symbols.charAt(new Random().nextInt(symbols.length())));
            }
            return builder.toString();
        } else return "";
    }

    private static String symbolSetSwitcher(String symbolsSet) {
        switch (symbolsSet) {
            case "integer":
                return "123456789";
            case "string":
                return "1234567890abcdefghijklmnopqrstuvwxyz";
            case "literal":
                return "abcdefghijklmnopqrstuvwxyz";
            default:
                return "";
        }
    }
}
