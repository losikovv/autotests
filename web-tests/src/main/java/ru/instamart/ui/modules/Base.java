package instamart.ui.modules;

import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import static instamart.core.settings.Config.CoreSettings.debug;
import static instamart.core.settings.Config.CoreSettings.verbose;

public class Base {
    static WebDriver driver;
    public static EnvironmentData environment;
    public static AppManager kraken;

    Base(WebDriver driver, EnvironmentData environment, AppManager app) {
        this.driver = driver;
        this.environment = environment;
        this.kraken = app;
    }

    /** Отправить сообщение в консоль */
    public static void message(String message) {
        //Reporter.log(message,false);
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
