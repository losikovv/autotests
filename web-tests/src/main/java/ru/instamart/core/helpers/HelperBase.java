package instamart.core.helpers;

import org.openqa.selenium.*;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.EnvironmentData;

import static instamart.core.settings.Config.CoreSettings.debug;
import static instamart.core.settings.Config.CoreSettings.verbose;

public class HelperBase {
    static WebDriver driver;
    static AppManager kraken;
    static EnvironmentData environment;
    private static boolean acceptNextAlert = true;

    HelperBase(WebDriver driver, EnvironmentData environment, AppManager app) {
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
