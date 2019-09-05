package ru.instamart.application.platform.helpers;

import org.openqa.selenium.*;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.ServerData;

import static ru.instamart.application.Config.CoreSettings.debug;
import static ru.instamart.application.Config.CoreSettings.verbose;

public class HelperBase {
    static WebDriver driver;
    static AppManager kraken;
    public String baseUrl;
    public String fullBaseUrl;
    public String adminUrl;
    private static boolean acceptNextAlert = true;

    HelperBase(WebDriver driver, ServerData server, AppManager app) {
        this.driver = driver;
        this.baseUrl = server.getBaseURL(false);
        this.fullBaseUrl = server.getBaseURL(true);
        this.adminUrl = server.getAdminURL();
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
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if(acceptNextAlert) {
                alert.accept();
            } else alert.dismiss();
            debugMessage("> handling alert [" + alertText + "]");
        } finally {
            acceptNextAlert = true;
        }
    }

    /** Удалить куки */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }
}
