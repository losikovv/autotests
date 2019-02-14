package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.OrderDetailsData;
import ru.instamart.autotests.models.UserData;

import java.util.Random;

import static ru.instamart.autotests.application.Config.*;

public class GenerationHelper extends HelperBase {

    GenerationHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /**
     * Сгенерировать int c рандомным числом указанной длины
     */
    public int integer(int length) {
        return Integer.parseInt(buildString("integer", length));
    }

    /**
     * Сгенерировать String c рандомным числом указанной длины
     */
    public String digitString(int length) { return buildString("integer", length); }

    /**
     * Сгенерировать рандомноую строку указанной длины
     */
    public String string(int length) {
        return buildString("string", length);
    }

    /**
     * Сгенерировать рандомноую буквенную строку указанной длины
     */
    public String literalString(int length) {
        return buildString("literal", length);
    }

    /**
     * Генератор строки с набором символов указанной длины
     */
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

    //TODO объединить методы testUserData, обернув один в другой

    /**
     * Сгенерировать стандартные реквизиты тестового пользователя
     */
    public UserData testUserData() {
        UserData testuser = new UserData(testEmail(8), companyName, companyName + " test", "user");
        System.out.println("Сгенерированы реквизиты тестового юзера");
        System.out.println("Имя: " + testuser.getName());
        System.out.println("Логин: " + testuser.getLogin());
        System.out.println("Пароль: " + testuser.getPassword() + "\n");
        return testuser;
    }

    /**
     * Сгенерировать реквизиты тестового пользователя со значениями указанной длины
     */
    public UserData testUserData(int reqsLength) {
        UserData testuser = new UserData(
                testEmail(reqsLength - testUserEmailBase.length() - 1),
                string(reqsLength),
                literalString(reqsLength),
                "user"
        );
        System.out.println("Test user requisites generated");
        System.out.println("name: " + testuser.getName());
        System.out.println("login: " + testuser.getLogin());
        System.out.println("password: " + testuser.getPassword() + "\n");
        return testuser;
    }

    /**
     * Сгенерировать реквизиты тестового пользователя в домене instamart.ru
     */
    public UserData testAdminData() {
        UserData testuser = new UserData(testAdminEmail(8), companyName, companyName + " test", "admin");
        System.out.println("Сгенерированы реквизиты тестового юзера");
        System.out.println("Имя: " + testuser.getName());
        System.out.println("Логин: " + testuser.getLogin());
        System.out.println("Пароль: " + testuser.getPassword() + "\n");
        return testuser;
    }

    /**
     * Сгенерировать тестовый имейл с префиксом указанной длины
     */
    private String testEmail(int prefixLength) {
        return literalString(prefixLength) + "-" + testUserEmailBase;
    }

    /**
     * Сгенерировать тестовый имейл в домене instamart.ru
     */
    private String testAdminEmail(int prefixLength) {
        return literalString(prefixLength) + "-" + testAdminEmailBase;
    }

    /**
     * Сгенерить случайную фразу
     */
    public String randomPhrase() {
        String startPage = kraken.grab().currentURL();
        kraken.get().url("https://konservs.com/tools/bredogenerator/");
        String phrase = kraken.grab().text(By.className("bred"));
        kraken.get().url(startPage);
        return (phrase);
    }

    public OrderDetailsData testOrderDetails() {
        return new OrderDetailsData();
    }
}
