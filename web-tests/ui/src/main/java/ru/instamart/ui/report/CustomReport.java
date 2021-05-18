package ru.instamart.ui.report;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import ru.instamart.ui.manager.AppManager;

import java.io.File;
import java.util.StringJoiner;

public final class CustomReport {

    /** Создаем скриншот и добавляем его в Allure */
    @Attachment(value = "Скриншот с веб страницы", type = "image/png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) AppManager.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    /** Создаем скриншот для добавления его в Qase */
    public static File takeScreenshotFile () {
        return ((TakesScreenshot) AppManager.getWebDriver()).getScreenshotAs(OutputType.FILE);
    }

    public static String browserLog() {
        final StringJoiner joiner = new StringJoiner("\n");
        final Logs logs = AppManager.getWebDriver().manage().logs();
        final LogEntries logEntries = logs.get(LogType.BROWSER);

        logEntries.forEach(log -> joiner.add(log.getMessage()));

        return joiner.toString();
    }

    public static String sourcePage() {
        return AppManager.getWebDriver().getPageSource();
    }
}
