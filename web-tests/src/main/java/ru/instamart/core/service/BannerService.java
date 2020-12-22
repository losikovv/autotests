package instamart.core.service;

import instamart.core.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public final class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);
    private static final String BANNER_URL = "banner.txt";
    private static final String RESOURCE = FileUtils.getResourceDir(BANNER_URL);

    public static void printBanner() {
        try(final BufferedReader in = new BufferedReader(new FileReader(RESOURCE))) {
            String line = in.readLine();
            while(line !=null) {
                System.out.println(line);
                line = in.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.error("File {} not found", BANNER_URL);
        } catch (IOException e) {
            logger.error("Cant' load or parse file", e);
        }
    }
}
