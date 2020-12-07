package instamart.core.listeners;

import instamart.core.service.QaseService;
import instamart.core.settings.Config;
import io.qase.api.enums.RunResultStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public final class ApiListener implements ITestListener {

    private final QaseService qaseService;

    public ApiListener() {
        Config.load();
        this.qaseService = new QaseService("APIAT");
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
