package ru.instamart.grpc.listener;

import io.qase.api.enums.RunResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import ru.instamart.kraken.service.QaseService;

@Slf4j
public final class GrpcListener implements ITestListener, ISuiteListener {

    private final QaseService qaseService;

    public GrpcListener() {
        String projectId = System.getProperty("qase.Project", "INAPI");
        String testRunName = System.getProperty("qase.Title", "API Test Run");
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
        this.qaseService.sendResult(result, RunResultStatus.failed);
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
            //this.qaseService.actualizeAutomatedTestCases();
        } catch (Exception e) {
            log.error("FATAL: something went wrong when try to finish test", e);
        }
    }
}
