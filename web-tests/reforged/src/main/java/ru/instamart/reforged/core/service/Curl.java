package ru.instamart.reforged.core.service;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
public final class Curl {

    @Getter
    private final List<String> opt;

    public Curl(List<String> opt) {
        this.opt = opt;
    }

    @Step("Проверка доступности страницы {0}")
    public static boolean pageAvailable(final String url, final String header) {
        final int code = getResponseCode(new Curl.Builder(url).withHeader(header).build());
        log.debug("Страница '{}' вернула код '{}'", url, code);
        return code == 200;
    }

    @Step("Проверка недоступности страницы {0}")
    public static boolean pageUnavailable(final String url, final String header) {
        final int code = getResponseCode(new Curl.Builder(url).withHeader(header).build());
        log.debug("Страница '{}' вернула код '{}'", url, code);
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

    public static final class Builder {

        private final List<String> opt;
        private final String url;

        public Builder(final String url) {
            this.opt = new ArrayList<>();
            this.url = url;
            this.opt.add("curl");
            this.opt.add("-s");
            this.opt.add("-o");
            this.opt.add("--head");
            this.opt.add("-w");
            this.opt.add("%{http_code}");
        }

        public Builder withHeader(final String header) {
            this.opt.add("-H");
            this.opt.add("sbm-forward-feature-version-stf:" + header);
            return this;
        }

        public Curl build() {
            this.opt.add(url);
            return new Curl(opt);
        }
    }
}
