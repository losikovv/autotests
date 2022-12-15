package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.kraken.service.QaseService;
import ru.sbermarket.qase.enums.RunResultStatus;

import java.lang.reflect.Method;

@Slf4j
public final class GrpcTestSuiteListener extends AllureTestNgListener {

    private final QaseService qaseService;

    public GrpcTestSuiteListener() {
        String projectId = System.getProperty("qase.Project", "PHUB");
        String testRunName = System.getProperty("qase.Title", "gRPC Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ISuite suite) {
        super.onStart(suite);
        this.qaseService.createTestRun();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        this.qaseService.sendResult(result, RunResultStatus.passed);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        this.qaseService.sendResult(result, RunResultStatus.failed);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        this.qaseService.sendResult(result, RunResultStatus.blocked);
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
        try {
            this.qaseService.completeTestRun();
        } catch (Exception e) {
            log.error("FATAL: something went wrong when try to finish test run", e);
        }
    }

    @Override
    protected void tearDown(Method method, ITestResult result) {

    }
}
