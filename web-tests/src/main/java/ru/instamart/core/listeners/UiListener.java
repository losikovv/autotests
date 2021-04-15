package ru.instamart.core.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import ru.instamart.core.service.QaseService;
import io.qase.api.enums.RunResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public final class UiListener implements ITestListener, ISuiteListener {

    private final QaseService qaseService;

    public UiListener() {
        String projectId = System.getProperty("qase.Project","STF");
        this.qaseService = new QaseService(projectId);
    }

    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onStart(ITestContext context) {
        this.qaseService.generateTestCasesList();
        this.qaseService.createTestRun();
    }

    @Override
    public void onFinish(ITestContext context) {
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
        this.qaseService.sendResult(result, RunResultStatus.failed, qaseService.uploadScreenshot());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        this.qaseService.sendResult(result, RunResultStatus.blocked);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            this.qaseService.completeTestRun();
            this.qaseService.deleteOldTestRuns();
            this.qaseService.deleteOldDefects();
        } catch (Exception e) {
            log.error("FATAL: something wrong when try to finish test", e);
        }
    }
}
