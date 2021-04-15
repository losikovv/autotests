package ru.instamart.core.service;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.core.util.FileUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;

@Slf4j
public final class BannerService {
    private static final String BANNER_URL = "banner.txt";
    private static final String RESOURCE = FileUtils.getResourceDir(BANNER_URL);

    public static void printBanner() {
        try(final BufferedReader in = new BufferedReader(new FileReader(RESOURCE))) {
            String line = in.readLine();
            StringJoiner banner = new StringJoiner("\n","\n","\n");
            while(line != null) {
                banner.add(line);
                line = in.readLine();
            }
            log.info(banner.toString());
        } catch (FileNotFoundException e) {
            log.error("File {} not found", BANNER_URL);
        } catch (IOException e) {
            log.error("Cant' load or parse file", e);
        }
    }
}
