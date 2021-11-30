package ru.instamart.reforged.core.report;

import com.google.common.base.Joiner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;
import ru.instamart.kraken.helper.SystemLogAttachmentHelper;
import ru.instamart.reforged.core.Kraken;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;

import static ru.instamart.kraken.helper.LogbackLogBuffer.clearLogbackLogBuffer;
import static ru.instamart.kraken.helper.LogbackLogBuffer.getLogbackBufferLog;

public final class CustomReport {

    @Attachment(value = "Системный лог", type = "text/plain")
    public static String addSystemLog() {
        final String result = SystemLogAttachmentHelper.getContent();
        SystemLogAttachmentHelper.stop();
        return result;
    }

    @Attachment(value = "Slf4j лог", type = "text/plain")
    public static String addCustomLog() {
        final String result = getLogbackBufferLog();
        clearLogbackLogBuffer();
        return result;
    }

    @Attachment(value = "Браузерный лог", type = "text/plain")
    public static String addBrowserLog() {
        final StringJoiner joiner = new StringJoiner("\n");
        Kraken.getLogs(LogType.BROWSER)
                .forEach(log -> joiner.add(log.getMessage()));

        return joiner.toString();
    }

    @Attachment(value = "Содержимое страницы", type = "text/html")
    public static String addSourcePage() {
        return Kraken.getSource();
    }

    @Attachment(value = "LocalStorage", type = "text/plain")
    public static String addLocalStorage() {
        final StringJoiner joiner = new StringJoiner("\n");
        final String[] storage = Kraken.jsAction().getLocalStorage().split(",");
        Arrays.stream(storage).forEach(joiner::add);

        return joiner.toString();
    }

    /** Создаем скриншот и добавляем его в Allure */
    @Attachment(value = "Скриншот с веб страницы", type = "image/jpg")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) Kraken.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    /** Создаем скриншот для добавления его в Qase */
    public static File takeScreenshotFile () {
        return ((TakesScreenshot) Kraken.getWebDriver()).getScreenshotAs(OutputType.FILE);
    }

    @Attachment(value = "Куки браузера", type = "text/plain")
    public static String addCookieLog() {
        final Set<Cookie> cookies = Kraken.getCookie();
        final int maxLineLength = cookies
                .stream()
                .map(Cookie::getValue)
                .mapToInt(String::length)
                .max().orElse(0);
        final int count = maxLineLength >= 20 ? Math.min(maxLineLength + 1, 120) : 20;
        final StringBuilder sb = new StringBuilder();
        final String delimiter = '+' + Joiner
                .on('+')
                .join(joinLine(40),
                        joinLine(count),
                        joinLine(40)) + "+\n";
        sb.append(delimiter);
        sb.append(String.format("|%-40s|%-" + count + "s|%-40s|%n", "Name", "Value", "Domain"));
        sb.append(delimiter);

        for (final Cookie e : cookies) {
            //TODO: Жутко, но по другому не придумал
            final String tmp = e.getValue().replaceAll(".{120}(?=.)", "$0\n");
            final String[] splitString = splitString(tmp);
            final int countLine = splitString.length;
            if (countLine > 1) {
                for (int i = 0; i < countLine; i++) {
                    if (i == 0) {
                        sb.append(String.format("|%-40s|%-" + count + "s|%-40s|%n", e.getName(), splitString[i], e.getDomain()));
                    } else {
                        sb.append(String.format("|%-40s|%-" + count + "s|%-40s|%n", "", splitString[i], ""));
                    }
                }
            } else {
                sb.append(String.format("|%-40s|%-" + count + "s|%-40s|%n", e.getName(), e.getValue(), e.getDomain()));
            }
            sb.append(delimiter);
        }

        return sb.toString();
    }

    private static String[] splitString(String str){
        return str.split("\r\n|\r|\n");
    }

    private static String joinLine(final int count) {
        return Joiner.on("").join(Collections.nCopies(count, "-"));
    }
}
