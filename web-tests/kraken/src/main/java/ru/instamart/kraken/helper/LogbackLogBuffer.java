package ru.instamart.kraken.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LogbackLogBuffer extends OutputStream {

    private static LogbackLogBuffer INSTANCE = new LogbackLogBuffer();
    private ThreadLocal<ByteArrayOutputStream> local = new ThreadLocal<ByteArrayOutputStream>() {
        protected ByteArrayOutputStream initialValue() {
            return new ByteArrayOutputStream();
        }
    };

    public static LogbackLogBuffer getInstance() {
        return INSTANCE;
    }

    public static void clearLogbackLogBuffer() {
        INSTANCE.local.get().reset();
    }

    public static String getLogbackBufferLog() {
        return new String(INSTANCE.local.get().toByteArray());
    }

    @Override
    public void write(int b) throws IOException {
        local.get().write(b);
    }
}
