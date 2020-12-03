package instamart.core.helpers;

import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.core.settings.Config.*;

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

    /** Отправить verbose-сообщение в консоль */
    public static void verboseMessage(Object message) {
        verboseMessage(message.toString());
    }

    /** Отправить verbose-сообщение в консоль */
    public static void verboseMessage(String message) {
        if(VERBOSE) {
            if (LOG) LOGGER.debug(message);
            else System.out.println(message);
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
            verboseMessage("> handling alert [" + alertText + "]");
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
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
