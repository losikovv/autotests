package ru.instamart.kraken.service;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Slf4j
public final class BannerService {

    private static final String BANNER_URL = "banner.txt";

    public static void printBanner() {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(FileUtils.getConfig(BANNER_URL, BannerService.class), StandardCharsets.UTF_8))) {
            String line = in.readLine();
            StringJoiner banner = new StringJoiner("\n", "\n", "\n");
            while (line != null) {
                banner.add(line);
                line = in.readLine();
            }
            log.debug(banner.toString());
        } catch (FileNotFoundException e) {
            log.error("File {} not found", BANNER_URL);
        } catch (IOException e) {
            log.error("Cant' load or parse file", e);
        }
    }

    public static void main(String[] args) {
        BannerService.printBanner();
    }
}
