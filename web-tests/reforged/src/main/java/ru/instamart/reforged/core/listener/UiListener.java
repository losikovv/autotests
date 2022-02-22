package ru.instamart.reforged.core.listener;

import ru.instamart.reforged.core.Kraken;
import ru.sbermarket.qase.enums.RunResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import ru.instamart.kraken.retry.RetryAnalyzer;
import ru.instamart.kraken.service.QaseService;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.report.CustomReport;

@Slf4j
public final class UiListener implements ITestListener, ISuiteListener {

    private final QaseService qaseService;

    public UiListener() {
        String projectId = System.getProperty("qase.Project", "STF");
        String testRunName = System.getProperty("qase.Title", "UI Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ISuite suite) {
    }

    @Override
    public void onStart(ITestContext context) {
        this.qaseService.createTestRun();
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        this.qaseService.sendResult(result, RunResultStatus.passed);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        fireRetryTest(result);
        this.qaseService.sendResult(result, RunResultStatus.failed, qaseService.uploadScreenshot(CustomReport.takeScreenshotFile()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        this.qaseService.sendResult(result, RunResultStatus.blocked);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            this.qaseService.completeTestRun();
        } catch (Exception e) {
            log.error("FATAL: something wrong when try to finish test run", e);
        }
    }

    private void fireRetryTest(final ITestResult result) {
        final var retryAnalyzer = result.getMethod().getRetryAnalyzer(result);
        //TODO: Wait Pattern Matching for instanceof Java 14++
        if (retryAnalyzer instanceof RetryAnalyzer) {
            final var retry = (RetryAnalyzer) retryAnalyzer;
            retry.retry(result);
        }
    }
}
