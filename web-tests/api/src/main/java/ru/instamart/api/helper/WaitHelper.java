package ru.instamart.api.helper;

import io.qameta.allure.Step;
import org.awaitility.core.ThrowingRunnable;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.awaitility.Awaitility.with;

public class WaitHelper {

    @Step("Ожидание успешного assert с max ожиданием {awaitSeconds} сек.")
    public static void withRetriesAsserted(final ThrowingRunnable assertion, final Integer awaitSeconds) {
        with()
                .pollInSameThread()
                .pollInterval(Duration.of(1, SECONDS))
                .await()
                .atMost(awaitSeconds, TimeUnit.SECONDS)
                .untilAsserted(assertion);
    }

    @Step("Ожидание успешного assert с max ожиданием {awaitSeconds} сек.")
    public static void withRetriesAsserted(final ThrowingRunnable assertion, final Callable<Boolean> failFast, final String failFastErrorMsg, final Integer awaitSeconds) {
        with()
                .pollInSameThread()
                .pollInterval(Duration.of(1, SECONDS))
                .await()
                .failFast(failFastErrorMsg, failFast)
                .atMost(awaitSeconds, TimeUnit.SECONDS)
                .untilAsserted(assertion);
    }

    @Step("Ожидание успешного условия с max ожиданием 300 сек.")
    public static void withRetries(long retryPollDelay, long retryPollInterval, String conditionDesc, Callable<Boolean> conditionEvaluator) {
        with()
                .pollDelay(retryPollDelay, TimeUnit.MILLISECONDS) // Задержка перед первым запросом
                .and()
                .pollInterval(retryPollInterval, TimeUnit.MILLISECONDS) // Интервал запроса
                .await(conditionDesc)
                .atMost(300, TimeUnit.SECONDS)
                .until(conditionEvaluator);
    }

    @Step("Ожидание успешного условия с max ожиданием 300 сек.")
    public static void withRetries(Callable<Boolean> conditionEvaluator) {
        with()
                .pollInSameThread()
                .pollInterval(Duration.of(1, SECONDS))
                .await()
                .atMost(300, TimeUnit.SECONDS)
                .until(conditionEvaluator);
    }
}
