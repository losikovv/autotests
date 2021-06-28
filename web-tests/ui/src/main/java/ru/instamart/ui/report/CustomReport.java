package ru.instamart.ui.report;

import com.google.common.base.Joiner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import ru.instamart.ui.manager.AppManager;

import java.io.File;
import java.util.*;

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
        return ((TakesScreenshot) AppManager.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Браузерный лог", type = "text/plain")
    public static String addBrowserLog() {
        final StringJoiner joiner = new StringJoiner("\n");
        final Logs logs = AppManager.getWebDriver().manage().logs();
        final LogEntries logEntries = logs.get(LogType.BROWSER);

        logEntries.forEach(log -> joiner.add(log.getMessage()));

        return joiner.toString();
    }

    @Attachment(value = "Содержимое страницы", type = "text/html")
    public static String addSourcePage() {
        return AppManager.getWebDriver().getPageSource();
    }

    /** Создаем скриншот для добавления его в Qase */
    public static File takeScreenshotFile () {
        return ((TakesScreenshot) AppManager.getWebDriver()).getScreenshotAs(OutputType.FILE);
    }

    @Attachment(value = "Куки браузера", type = "text/plain")
    public static String addCookieLog(final String title) {
        final Set<Cookie> cookies = AppManager.getWebDriver().manage().getCookies();
        final OptionalInt maxLineLength = cookies
                .stream()
                .filter(Objects::nonNull)
                .map(Cookie::getValue)
                .map(String::length)
                .mapToInt(Integer::intValue)
                .max();
        final int count = maxLineLength.orElse(0) >= 20 ? maxLineLength.getAsInt() + 1:20;
        final StringBuilder sb = new StringBuilder();
        sb.append("Report for ").append(title).append('\n');

        final String delimiter = '+' + Joiner
                .on('+')
                .join(joinLine(10),
                        joinLine(count),
                        joinLine(10)) + "+\n";
        sb.append(delimiter);
        sb.append(String.format("|%-10s|%-" + count + "s|%-10s|%n", "Name", "Value", "Domain"));
        sb.append(delimiter);

        for (final Cookie e : cookies) {
            sb.append(String.format("|%-10s|%-" + count + "s|%-10s|%n", e.getName(), e.getValue(), e.getDomain()));
        }

        sb.append(delimiter);

        return sb.toString();
    }

    private static String joinLine(final int count) {
        return Joiner.on("").join(Collections.nCopies(count, "-"));
    }
}
