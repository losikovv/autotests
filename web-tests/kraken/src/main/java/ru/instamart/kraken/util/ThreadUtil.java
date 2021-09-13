package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.DoubleAdder;

@Slf4j
public class ThreadUtil {

    public static final DoubleAdder ALL_WAIT_TIME = new DoubleAdder();

    /** Просто задержка на указанное время */
    public static void simplyAwait(final double seconds) {
        log.debug("Задержка на {} сек.", seconds);
        try {
            ALL_WAIT_TIME.add(seconds);
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException i) {
            log.error("Прервано");
        }
    }

    private ThreadUtil() {}
}
