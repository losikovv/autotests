package instamart.core.helpers;

import instamart.core.common.AppManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class HelperBase {
    public static WebDriver driver;
    public static AppManager kraken;
    private static boolean acceptNextAlert = true;
    private static final Logger log = LoggerFactory.getLogger(HelperBase.class);

//    public HelperBase(WebDriver driver, AppManager app) {
//        this.driver = driver;
//        this.kraken = app;
//    }

    public HelperBase(final WebDriver driver, final AppManager app) {
        HelperBase.driver = driver;
        kraken = app;
    }

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    public static void handleAlert() {
        try {
            final Alert alert = driver.switchTo().alert();
            final String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else alert.dismiss();
            log.info("> handling alert [{}]", alertText);
        } finally {
            acceptNextAlert = true;
        }
    }

    /** Удалить куки */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    /** Создаем скриншот и добавляем его в Allure */
    @Attachment(value = "Скриншот с веб страницы", type = "image/png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /** Создаем скриншот для добавления его в Qase */
    public static File takeScreenshotFile () {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}
