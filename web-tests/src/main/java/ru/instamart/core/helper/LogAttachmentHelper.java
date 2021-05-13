package ru.instamart.core.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public final class LogAttachmentHelper {

    private static PrintStream originalStream;
    private static OutputStreamRouter router;

    public static synchronized void start() {
        if (router == null) {
            originalStream = System.out;
            router = new OutputStreamRouter(originalStream);
            System.setOut(new PrintStream(router));
        }
        router.registryThread(Thread.currentThread());
    }

    public static String getContent() {
        return getContent(true);
    }

    public static synchronized String getContent(final boolean clear) {
        if (router == null) {
            throw new RuntimeException();
        }
        return router.fetchThreadContent(Thread.currentThread(), clear);
    }

    public static synchronized void stop() {
        if (router == null) {
            throw new RuntimeException();
        }
        router.unregistryThread(Thread.currentThread());
        if (router.getActiveRoutes() == 0) {
            System.setOut(originalStream);
            originalStream = null;
            router = null;
        }
    }

    private static final class OutputStreamRouter extends OutputStream {

        private final HashMap<Thread, ByteArrayOutputStream> loggerStreams = new HashMap<>();
        private final OutputStream original;

        public OutputStreamRouter(final OutputStream original) {
            this.original = original;
        }

        public void registryThread(final Thread thread) {
            if (loggerStreams.containsKey(thread)) throw new RuntimeException();
            loggerStreams.put(thread, new ByteArrayOutputStream(4096));
        }

        public void unregistryThread(final Thread thread) {
            if (!loggerStreams.containsKey(thread)) throw new RuntimeException();
            loggerStreams.remove(thread);
        }

        public int getActiveRoutes() {
            return loggerStreams.size();
        }

        public String fetchThreadContent(final Thread thread, final boolean clear) {
            if (!loggerStreams.containsKey(thread)) throw new RuntimeException();
            final String result = loggerStreams.get(thread).toString();
            if (clear) {
                loggerStreams.get(thread).reset();
            }
            return result;
        }

        @Override
        public synchronized void write(final int b) throws IOException {
            original.write(b);
            if (loggerStreams.containsKey(Thread.currentThread())) {
                loggerStreams.get(Thread.currentThread()).write(b);
            }
        }

        @Override
        public synchronized void flush() throws IOException {
            original.flush();
        }

        @Override
        public synchronized void close() throws IOException {
            original.close();
        }
    }
}
