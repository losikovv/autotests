package ru.instamart.kraken.util;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.DoubleAdder;

@Slf4j
public final class ThreadUtil {

    public static final DoubleAdder ALL_WAIT_TIME = new DoubleAdder();

    private ThreadUtil() {}

    /** Просто задержка на указанное время */
    @Step("Ожидание: {seconds} sec.")
    public static void simplyAwait(final double seconds) {
        log.debug("Задержка на {} сек.", seconds);
        try {
            ALL_WAIT_TIME.add(seconds);
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException i) {
            log.error("Прервано");
        }
    }
}
