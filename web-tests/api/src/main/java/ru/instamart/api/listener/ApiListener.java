package ru.instamart.api.listener;

import io.qase.api.enums.RunResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import ru.instamart.kraken.service.QaseService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public final class ApiListener implements ITestListener, ISuiteListener {

    private final QaseService qaseService;

    public ApiListener() {
        String projectId = System.getProperty("qase.Project", "INAPI");
        String testRunName = System.getProperty("qase.Title", "API Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ISuite suite) {
        long count;
        try {
            count = Files.walk(Paths.get("build/allure-results"))
                    .filter(Files::isRegularFile)
                    .count();
            if (count <= 1) {
                this.qaseService.createTestRun();
            }
        } catch (IOException e) {
            log.debug("Ошибка чтения файлов: {}", e.getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
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
        } catch (Exception e) {
            log.error("FATAL: something went wrong when try to finish test run", e);
        }
    }
}
