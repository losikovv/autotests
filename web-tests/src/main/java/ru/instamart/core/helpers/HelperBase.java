package instamart.core.helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.core.settings.Config.CoreSettings.*;

public class HelperBase {
    static WebDriver driver;
    static AppManager kraken;
    static EnvironmentData environment;
    private static boolean acceptNextAlert = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(HelperBase.class);

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
            if (log) LOGGER.debug(message);
            else message(message);
        }
    }

    /** Отправить debug-сообщение в консоль */
    public static void debugMessage(String message) {
        if(debug) {
            if (log) LOGGER.debug(message);
            else message(message);
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

    /** Создаем скриншот и добавляем его в Allure*/
    @Attachment(value = "Скриншот с веб страницы", type = "image/png")
    public static byte[] takeScreenshot() {
        // Take a screenshot as byte array and return
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
