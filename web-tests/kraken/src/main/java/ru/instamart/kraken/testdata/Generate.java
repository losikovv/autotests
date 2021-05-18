package ru.instamart.kraken.testdata;

import ru.instamart.kraken.listener.ExecutionListener;

import java.util.Random;
import java.util.UUID;

public final class Generate {

    public static String testRunId() {
        return literalString(9).toUpperCase();
    }

    public static String userId() {
        return digitalString(7);
    }

    /**
     * Сгенерировать рандомноую строку указанной длины
     */
    public static String string(int length) {
        return Generate.string("digital", length);
    }

    /** Сгенерировать рандомноую буквенную строку указанной длины */
    public static String literalString(int length) {
        return Generate.string("literal", length);
    }

    /** Сгенерировать рандомноую цифровую строку указанной длины*/
    public static String digitalString(int length) {
        return Generate.string("digital", length);
    }

    /** Сгенерировать int c рандомным числом указанной длины */
    public static int integer(int length) {
        return Integer.parseInt(digitalString(length));
    }

    public static String testUserPhone(String userId) {
        return "777" + userId;
    }

    public static String testUserName(String role) {
        return TestVariables.TestParams.testMark + "-" + role + " " + ExecutionListener.runId;
    }
    /** Сгенерировать тестовый номер телефона */
    public static String phoneNumber(){
        String phone = "9999";
        return phone + digitalString(6);
    }

    /** Сгенерировать тестовый email */
    public static String email() {
        return UUID.randomUUID() + "@example.com";
    }

    /** Сгенерировать тестовый email */
    public static String emailAdmin() {
        return UUID.randomUUID() + "@instamart.ru";
    }

    private static String string(String symbols, int length) {
        return buildString(symbols, length);
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
            case "digital":
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
