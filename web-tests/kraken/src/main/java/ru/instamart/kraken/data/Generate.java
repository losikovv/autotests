package ru.instamart.kraken.data;

import ru.instamart.kraken.listener.ExecutionListener;

import java.security.SecureRandom;
import java.util.*;

public final class Generate {

    public static final char[] upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] lowerChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] numbers = "1234567890".toCharArray();
    public static final char[] specialChars = "@#$*&".toCharArray();

    public static String testRunId() {
        return literalString(9).toUpperCase();
    }

    public static String userId() {
        return digitalString(9);
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

    /**
     * Сгенерировать рандомноую кириллическую буквенную строку указанной длины
     */
    public static String literalCyrillicString(int length) {
        return Generate.string("cyrillic", length);
    }

    /**
     * Сгенерировать рандомноую цифровую строку указанной длины
     */
    public static String digitalString(int length) {
        return Generate.string("digital", length);
    }

    /**
     * Сгенерировать рандомноую символьную строку указанной длины
     */
    public static String symbolString(int length) {
        return Generate.string("symbol", length);
    }

    /**
     * Сгенерировать int c рандомным числом указанной длины
     */
    public static int integer(int length) {
        return Integer.parseInt(digitalString(length));
    }

    /**
     * Сгенерировать long c рандомным числом указанной длины
     */
    public static long longInt(int length) {
        return Long.parseLong(digitalString(length));
    }

    public static String testUserPhone(String userId) {
        return "9" + userId;
    }

    public static String testUserName(String role) {
        return TestVariables.TestParams.testMark + "-" + role + " " + ExecutionListener.runId;
    }
    /** Сгенерировать тестовый номер телефона */
    public static String phoneNumber(){
        String phone = "972";
        return phone + digitalString(7);
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
            case "cyrillic":
                return "абвгдежзийклмнопрстуфхцчшщъыьэюя";
            case "symbol":
                return "`~!@#$%^&*()?+=.,\\|/";
            default:
                return "";
        }
    }

    public static String generatePassword(int size, boolean useUpper, boolean useLower, boolean useNumbers, boolean useSpecial) {
        if (size < 4) size = 4;
        final var random = new SecureRandom();
        final var password = new StringBuilder();
        String charSet = "";

        if (useUpper) charSet += upperChars;
        if (useLower) charSet += lowerChars;
        if (useNumbers) charSet += numbers;
        if (useSpecial) charSet += specialChars;

        for (int i = 0; i < size; i++) {
            password.append(charSet.toCharArray()[random.nextInt(charSet.length() - 1)]);
        }

        if (useLower)
            password.setCharAt(0, lowerChars[random.nextInt(lowerChars.length)]);
        if (useUpper)
            password.setCharAt(1, upperChars[random.nextInt(upperChars.length)]);
        if (useNumbers)
            password.setCharAt(2, numbers[random.nextInt(numbers.length)]);
        if (useSpecial)
            password.setCharAt(3, specialChars[random.nextInt(specialChars.length)]);

        return String.valueOf(password);
    }

    /** Сгенерировать 10-значный ИНН для Юр. лиц или 12-значный ИНН для Физ. лиц и ИП*/
    public static String generateINN(int symbols){
        switch (symbols){
            case 10:
                return checkINN(inn10());
            case 12:
                return inn12();
            default:
                return "";
        }
    }

    private static String inn10(){
        StringBuilder builder = new StringBuilder();
        List<Integer> weights = Arrays.asList(2, 4, 10, 3, 5, 9, 4, 6, 8); // веса для контрольной суммы
        int controlSumm = 0; // контрольная сумма первых 9 цифр инн
        int controlNumber; //контрольная цифра от суммы первых 9 цифр инн
        for (int i = 0; i < weights.size(); i++) {
            int rand = new Random().nextInt(10);
            // Первые 2 цифры не могут быть равны 00. Если первая цифра 0 - берём цифру от 1 до 9
            if (i == 1 && controlSumm == 0){
                rand = (int)Math.floor(Math.random()*(9-1+1)+1);
            }
            builder.append(rand);
            controlSumm += rand * weights.get(i);
        }
        controlNumber = controlSumm % 11 % 10;
        builder.append(controlNumber);
        return builder.toString();
    }

    private static String inn12(){
        StringBuilder builder = new StringBuilder();
        List<Integer> weights1 = Arrays.asList(7,2,4,10,3,5,9,4,6,8); // веса для первой контрольной суммы
        List<Integer> weights2 = Arrays.asList(3,7,2,4,10,3,5,9,4,6,8); // веса для второй контрольной суммы
        ArrayList<Integer> currentINN = new ArrayList<>(); //Для хранения первых 11 цифр инн
        int controlSumm1 = 0; // контрольная сумма первых 10 цифр инн
        int controlSumm2 = 0; // контрольная сумма первых 11 цифр инн
        int controlNumber1; //контрольная цифра от суммы первых 10 цифр инн
        int controlNumber2; //контрольная цифра от суммы первых 11 цифр инн
        for (int i = 0; i < weights1.size(); i++) {
            int rand = new Random().nextInt(10);
            // Первые 2 цифры не могут быть равны 00. Если первая цифра 0 - берём цифру от 1 до 9
            if (i == 1 && controlSumm1 == 0){
                rand = (int)Math.floor(Math.random()*(9-1+1)+1);
            }
            builder.append(rand);
            currentINN.add(rand);
            controlSumm1 += rand * weights1.get(i);
        }
        controlNumber1 = controlSumm1 % 11 % 10;
        builder.append(controlNumber1);
        currentINN.add(controlNumber1);
        for (int i = 0; i < currentINN.size(); i++){
            controlSumm2 += currentINN.get(i) * weights2.get(i);
        }
        controlNumber2 = controlSumm2 % 11 % 10;
        builder.append(controlNumber2);
        return builder.toString();

    }
    /** ИНН 7774453691 - константа для компании с отрицательным балансом,
    ИНН 5978081649 - для компании с положительным балансом.
    Проверяем, что  ИНН созданной компании не совпадает с константными. */
    private static String checkINN (String inn){
        while (inn.equals("7774453691") || inn.equals("5978081649")){
            inn = inn10();
        }
        return inn;
    }
}
