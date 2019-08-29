package ru.instamart.application.platform.modules;

import org.openqa.selenium.*;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.ServerData;

import static ru.instamart.application.Config.CoreSettings.debug;
import static ru.instamart.application.Config.CoreSettings.verbose;

public class Base {
    static WebDriver driver;
    static AppManager kraken;
    public String baseUrl;
    public static String fullBaseUrl;
    public String adminUrl;

    Base(WebDriver driver, ServerData environment, AppManager app) {
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
