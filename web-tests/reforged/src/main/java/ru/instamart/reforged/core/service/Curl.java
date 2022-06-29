package ru.instamart.reforged.core.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public final class Curl {

    // -s скрывает прогресс
    // -o записать овтет в
    // --head получить только заголовок
    // -w вывести только
    private static final String CURL_RESPONSE_CODE = "curl -s -o --head -w \"%{http_code}\" ";

    @Step("Проверка доступности страницы {0}")
    public static boolean pageAvailable(final String url) {
        final int code = getResponseCode(url);
        log.debug("Страница '{}' вернула код '{}'", url, code);
        return code == 200;
    }

    @Step("Проверка недоступности страницы {0}")
    public static boolean pageUnavailable(final String url) {
        final int code = getResponseCode(url);
        log.debug("Страница '{}' вернула код '{}'", url, code);
        return code == 404;
    }

    public static int getResponseCode(final String url) {
        final Process process;
        try {
            process = Runtime.getRuntime().exec(CURL_RESPONSE_CODE + url);
            return StringUtil.extractNumberFromString(readResponse(process.getInputStream()));
        } catch (IOException e) {
            log.error("FATAL: Crash while exec curl request with url={}", url);
        }
        return 0;
    }

    private static String readResponse(final InputStream inputStream) {
        final StringBuilder sb = new StringBuilder();
        String line = null;
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("FATAL: can't extract string from curl response");
        }
        return sb.toString();
    }

    private Curl() {}
}
