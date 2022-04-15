package ru.instamart.kraken.helper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

import static org.testng.Assert.fail;

@Slf4j
public enum ServiceHelper {

    INSTANCE;

    public int getFreePort() {
        try {
            ServerSocket s = create(new int[]{35432, 5432, 5433, 5434});
            log.debug("listening on port: {}", s.getLocalPort());
            return s.getLocalPort();
        } catch (IOException ex) {
            log.error("no available ports");
            fail("");
        }
        return 0;
    }

    private ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue;
            }
        }
        throw new IOException("no free port found");
    }


}
