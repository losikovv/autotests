package instamart.core.listeners;

import instamart.core.service.QaseService;
import io.qase.api.enums.RunResultStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

public final class UiListener implements ITestListener {

    private final QaseService qaseService;

    public UiListener() {
        String projectId = System.getProperty("qase.Project","STF");
        this.qaseService = new QaseService(projectId);
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
        List<String> attachments = this.qaseService.uploadScreenshot();
        this.qaseService.sendResult(result, RunResultStatus.failed, attachments);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        this.qaseService.sendResult(result, RunResultStatus.blocked);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}
