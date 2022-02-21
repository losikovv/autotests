package ru.instamart.kraken.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static java.util.Objects.nonNull;

/**
 * Устанавливается для конкретного теста через
 * {@code @Test(retryAnalyzer = RetryAnalyzer.class)}
 */
public final class RetryAnalyzer implements IRetryAnalyzer {

    private static final int DEFAULT_MAX_RETRY = 1;
    private int currentRetryCount;

    @Override
    public boolean retry(ITestResult result) {
        final var annotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Retry.class);
        final var retryCount = nonNull(annotation) ? annotation.count() : DEFAULT_MAX_RETRY;
        if (!result.isSuccess()) {
            if (currentRetryCount < retryCount) {
                currentRetryCount++;
                result.setStatus(ITestResult.SUCCESS);
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        }
        return false;
    }
}
