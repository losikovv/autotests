package ru.instamart.core.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtil {

    /** Просто задержка на указанное время */
    public static void simply(double seconds) {
        log.info("Задержка на {} сек.", seconds);
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException i) {
            log.error("Прервано");
        }
    }

    private ThreadUtil() {}
}
