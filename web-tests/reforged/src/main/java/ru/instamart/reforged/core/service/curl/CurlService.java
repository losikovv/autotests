package ru.instamart.reforged.core.service.curl;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public final class CurlService {

    @Step("Проверка доступности страницы {0}")
    public static boolean pageAvailable(final Curl curl) {
        final int code = getResponseCode(curl);
        log.debug("Curl '{}' вернул код '{}'", curl, code);
        return code == 200;
    }

    @Step("Проверка недоступности страницы {0}")
    public static boolean pageUnavailable(final Curl curl) {
        final int code = getResponseCode(curl);
        log.debug("Curl '{}' вернул код '{}'", curl, code);
        return code == 404;
    }

    public static int getResponseCode(final Curl curl) {
        final Process process;
        try {
            final var pb = new ProcessBuilder(curl.getOpt());
            process = pb.start();
            final var result = StringUtil.extractNumberFromString(readResponse(process.getInputStream()));
            process.destroy();
            return result;
        } catch (IOException e) {
            log.error("FATAL: Crash while exec curl request with url={}", curl);
        }
        return 0;
    }

    private static String readResponse(final InputStream inputStream) {
        final var sb = new StringBuilder();
        try (final var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            log.error("FATAL: can't extract string from curl response");
        }
        return sb.toString();
    }
}
