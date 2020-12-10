package instamart.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);
    private static final String BANNER_URL = "src/test/resources/banner.txt";

    public static void printBanner() {
        try(final BufferedReader in = new BufferedReader(new FileReader(BANNER_URL));) {
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
