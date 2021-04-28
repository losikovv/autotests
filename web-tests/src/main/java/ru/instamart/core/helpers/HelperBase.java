package ru.instamart.core.helpers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import ru.instamart.core.common.AppManager;

@Slf4j
public class HelperBase {

    private static boolean acceptNextAlert = true;

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    public static void handleAlert() {
        try {
            final Alert alert = AppManager.getWebDriver().switchTo().alert();
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
        AppManager.getWebDriver().manage().deleteAllCookies();
    }
}
