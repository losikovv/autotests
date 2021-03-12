package instamart.core.listeners;

import instamart.core.service.QaseService;
import io.qase.api.enums.RunResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public final class ApiListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(ApiListener.class);

    private final QaseService qaseService;

    public ApiListener() {
        String projectId = System.getProperty("qase.Project","INAPI");
        this.qaseService = new QaseService(projectId);
    }

    @Override
    public void onStart(ITestContext context) {
        this.qaseService.generateTestCasesList();
        this.qaseService.createTestRun();
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            this.qaseService.deleteOldTestRuns();
            this.qaseService.deleteOldDefects();
        } catch (Exception e) {
            logger.error("FATAL: Can't remove old Test Runs or Defects", e);
        }
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
}
