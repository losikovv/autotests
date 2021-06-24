package ru.instamart.reforged.core.report;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.service.KrakenDriver;

import java.io.File;
import java.util.StringJoiner;

public final class CustomReport {

    @Attachment(value = "Системный лог", type = "text/plain")
    public static String addSystemLog() {
        final String result = LogAttachmentHelper.getContent();
        LogAttachmentHelper.stop();
        return result;
    }

    /** Создаем скриншот и добавляем его в Allure */
    @Attachment(value = "Скриншот с веб страницы", type = "image/jpg")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) KrakenDriver.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Браузерный лог", type = "text/plain")
    public static String addBrowserLog() {
        final StringJoiner joiner = new StringJoiner("\n");
        final Logs logs = Kraken.logs();
        final LogEntries logEntries = logs.get(LogType.BROWSER);

        logEntries.forEach(log -> joiner.add(log.getMessage()));

        return joiner.toString();
    }

    @Attachment(value = "Содержимое страницы", type = "text/html")
    public static String addSourcePage() {
        return KrakenDriver.getWebDriver().getPageSource();
    }

    /** Создаем скриншот для добавления его в Qase */
    public static File takeScreenshotFile () {
        return ((TakesScreenshot) KrakenDriver.getWebDriver()).getScreenshotAs(OutputType.FILE);
    }
}
