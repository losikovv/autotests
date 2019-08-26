package ru.instamart.autotests.appmanager.platform;

import org.openqa.selenium.*;
import ru.instamart.autotests.models.EnvironmentData;

import static ru.instamart.autotests.application.Config.CoreSettings.debug;
import static ru.instamart.autotests.application.Config.CoreSettings.verbose;

public class Base {
    static WebDriver driver;
    public String baseUrl;
    public static String fullBaseUrl;
    public String adminUrl;

    Base(WebDriver driver, EnvironmentData environment) {
        this.driver = driver;
        this.baseUrl = environment.getBaseURL(false);
        this.fullBaseUrl = environment.getBaseURL(true);
        this.adminUrl = environment.getAdminURL();
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
}
