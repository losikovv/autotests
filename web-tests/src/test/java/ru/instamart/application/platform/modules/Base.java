package ru.instamart.application.platform.modules;

import org.openqa.selenium.*;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.EnvironmentData;

import static ru.instamart.application.Config.CoreSettings.debug;
import static ru.instamart.application.Config.CoreSettings.verbose;

public class Base {
    static WebDriver driver;
    public static EnvironmentData environment;
    static AppManager kraken;

    Base(WebDriver driver, EnvironmentData environment, AppManager app) {
        this.driver = driver;
        this.environment = environment;
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
