package ru.instamart.autotests.appmanager.platform;

import org.openqa.selenium.*;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.appmanager.models.EnvironmentData;

import static ru.instamart.autotests.application.Config.CoreSettings.debug;
import static ru.instamart.autotests.application.Config.CoreSettings.verbose;

public class Base {
    static WebDriver driver;
    static ApplicationManager kraken;
    public String baseUrl;
    public static String fullBaseUrl;
    public String adminUrl;

    Base(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        this.driver = driver;
        this.baseUrl = environment.getBaseURL(false);
        this.fullBaseUrl = environment.getBaseURL(true);
        this.adminUrl = environment.getAdminURL();
        this.kraken = app;
    }

    /** Отправить сообщение в консоль */
    public static void message(String message) {
        System.out.println(message);
    }

    /** Отправить verbose-сообщение в консоль */
    public static void verboseMessage(String message) {
        if(verbose) {
            message(message);
        }
    }

    /** Отправить debug-сообщение в консоль */
    public static void debugMessage(String message) {
        if(debug) {
            message(message);
        }
    }

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    public static void handleAlert() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        debugMessage("> handling alert [" + alertText + "]");
    }
}
