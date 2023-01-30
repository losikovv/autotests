package ru.instamart.api.helper;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.awaitility.Awaitility.with;

public class WaitHelper {

    public static void withRetries(long retryPollDelay, long retryPollInterval, String conditionDesc, Callable<Boolean> conditionEvaluator) {
        with()
                .pollDelay(retryPollDelay, TimeUnit.MILLISECONDS)
                .and()
                .pollInterval(retryPollInterval, TimeUnit.MILLISECONDS)
                .await(conditionDesc)
                .atMost(300, TimeUnit.SECONDS)
                .until(conditionEvaluator);
    }

    public static void withRetries(Callable<Boolean> conditionEvaluator) {
        with()
                .pollInSameThread()
                .pollInterval(Duration.of(1, SECONDS))
                .await()
                .atMost(300, TimeUnit.SECONDS)
                .until(conditionEvaluator);
    }
}
