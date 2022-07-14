package ru.instamart.reforged.core.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public final class Curl {

    @Step("Проверка доступности страницы {0}")
    public static boolean pageAvailable(final String url, final String header) {
        final int code = getResponseCode(
                new Builder()
                        .setUrl(url)
                        .setHeader("sbm-forward-feature-version-stf", header)
                        .getCurl());
        log.debug("Страница '{}' вернула код '{}'", url, code);
        return code == 200;
    }

    @Step("Проверка недоступности страницы {0}")
    public static boolean pageUnavailable(final String url, final String header) {
        final int code = getResponseCode(
                new Builder()
                        .setUrl(url)
                        .setHeader("sbm-forward-feature-version-stf", header)
                        .getCurl());
        log.debug("Страница '{}' вернула код '{}'", url, code);
        return code == 404;
    }

    public static int getResponseCode(final String curl) {
        Process process;
        int code;
        try {
            process = Runtime.getRuntime().exec(curl);
            code = StringUtil.extractNumberFromString(readResponse(process.getInputStream()));
            process.destroy();
            return code;
        } catch (IOException e) {
            log.error("FATAL: Crash while exec curl request with url={}", curl);
        }
        return 0;
    }

    private static String readResponse(final InputStream inputStream) {
        final StringBuilder sb = new StringBuilder();
        String line = null;
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("FATAL: can't extract string from curl response");
        }
        return sb.toString();
    }

    public static class Builder {
        private final Map<String, String> headers = new HashMap<>();
        private String url;

        public Builder() {
        }

        private static void appendHeader(final StringBuilder builder, final String key, final String value) {
            builder.append("-H ")
                    .append(key)
                    .append(":")
                    .append(value)
                    .append(" ");
        }

        public Builder setHeader(final String name, final String value) {
            Objects.requireNonNull(name, "Header name must not be null value");
            Objects.requireNonNull(value, "Header value must not be null value");
            this.headers.put(name, value);
            return this;
        }

        public Builder setUrl(final String url) {
            this.url = url;
            return this;
        }

        public Curl build() {
            return new Curl();
        }

        private String getCurl() {
            final StringBuilder builder = new StringBuilder("curl -s -o --head ");
            headers.forEach((key, value) -> appendHeader(builder, key, value));
            builder.append(" -w \"%{http_code}\" ");
            builder.append(url);
            return builder.toString();
        }

    }
}
