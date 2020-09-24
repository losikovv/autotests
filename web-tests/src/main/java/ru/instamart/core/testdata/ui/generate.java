package instamart.core.testdata.ui;

import instamart.core.common.AppManager;
import instamart.core.settings.Config;
import instamart.ui.common.pagesdata.JuridicalData;
import instamart.ui.common.pagesdata.UserData;

import java.util.Random;

import static instamart.core.settings.Config.CoreSettings.debug;

public class generate {

    public static String testRunId() {
        return literalString(9).toUpperCase();
    }

    public static String userId() {
        return digitalString(7);
    }

    public static JuridicalData juridical() {
        return new JuridicalData(
                "ЗАО \"Лидер-" + generate.digitalString(4) + "\"",
                generate.string(8),
                generate.digitalString(12),
                generate.digitalString(9),
                generate.digitalString(20),
                generate.digitalString(9),
                generate.string(8),
                generate.digitalString(20)
        );
    }

    /**
     * Сгенерировать рандомноую строку указанной длины
     */
    public static String string(int length) {
        return generate.string("digital", length);
    }

    /** Сгенерировать рандомноую буквенную строку указанной длины */
    public static String literalString(int length) {
        return generate.string("literal", length);
    }

    /** Сгенерировать рандомноую цифровую строку указанной длины*/
    public static String digitalString(int length) {
        return generate.string("digital", length);
    }

    /** Сгенерировать int c рандомным числом указанной длины */
    public static int integer(int length) {
        return Integer.parseInt(digitalString(length));
    }

    public static String testUserPhone(String userId) {
        return "777" + userId;
    }

    public static String testUserName(String role) {
        return Config.TestVariables.TestParams.testMark + "-" + role + " " + AppManager.testrunId;
    }

    public static String testUserEmail(String role, String userId) {
        if(role.equals("admin")) {
            return userId + "-" + AppManager.testrunId + "-" + Config.TestVariables.TestParams.testMark + "@" + Config.TestVariables.CompanyParams.companyDomain;
        } else {
            return userId + "-" + AppManager.testrunId + "-" + Config.TestVariables.TestParams.testMark + "@" + Config.TestVariables.TestParams.testDomain;}
    }

    public static UserData testCredentials(String role) {
        return testCredentials(role, 5);
    }

    public static UserData testCredentials(String role, int prefixLength) {
        String testUserId = generate.userId();
        UserData testuser = new UserData(role, testUserEmail(role, testUserId), testUserPhone(testUserId), Config.TestVariables.CompanyParams.companyName, testUserName(role));
        if (prefixLength > 0) {
            String prefix = literalString(prefixLength);
            testuser.setLogin(prefix + "-" + testuser.getLogin());
            testuser.setPassword(prefix + "-" + testuser.getPassword());
            testuser.setName(prefix + "-" + testuser.getName());
        }
        if(debug) {
            System.out.println("Сгенерированы тестовые реквизиты для роли " + role);
            System.out.println("Телефон: " + testuser.getPhone());
            System.out.println("Email: " + testuser.getLogin());
            System.out.println("Пароль: " + testuser.getPassword());
            System.out.println("Имя: " + testuser.getName());
            System.out.println("\n");
        }
        return testuser;
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
