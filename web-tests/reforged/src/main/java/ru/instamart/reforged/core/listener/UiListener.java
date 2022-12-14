package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.instamart.kraken.service.QaseService;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.report.CustomReport;
import ru.sbermarket.qase.enums.RunResultStatus;

@Slf4j
public final class UiListener extends UiDefaultListener {

    private final QaseService qaseService;

    public UiListener() {
        super();
        final var projectId = System.getProperty("qase.Project", "STF");
        final var testRunName = System.getProperty("qase.Title", "UI Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
        this.qaseService.createTestRun();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        super.beforeInvocation(method, testResult);
        if (method.isTestMethod())
            addCookie(method);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        this.qaseService.sendResult(result, RunResultStatus.passed);
        stopFake(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        stopFake(result);
        if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        fireRetryTest(result);
        this.qaseService.sendResult(result, RunResultStatus.failed, qaseService.uploadScreenshot(CustomReport.takeScreenshotFile()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        this.qaseService.sendResult(result, RunResultStatus.blocked);
        stopFake(result);
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
        try {
            this.qaseService.completeTestRun();
        } catch (Exception e) {
            log.error("FATAL: something wrong when try to finish test run", e);
        }
    }
}
