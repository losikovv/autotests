package ru.instamart.kraken.util;

import java.io.IOException;
import java.net.ServerSocket;

public final class Socket {

    public static int findAvailablePort() {
        int port = 0;
        try (final var socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
        } catch (IOException ignored) {}
        if (port > 0) {
            return port;
        }
        throw new RuntimeException("Could not find a free port");
    }

    private Socket() {}
}
