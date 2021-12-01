package ru.instamart.kraken.appender;

import ch.qos.logback.core.OutputStreamAppender;
import ru.instamart.kraken.helper.LogbackLogBuffer;

public class SimpleAppender<E> extends OutputStreamAppender<E> {
    @Override
    public void start() {
        setOutputStream(LogbackLogBuffer.getInstance());
        super.start();
    }
}
