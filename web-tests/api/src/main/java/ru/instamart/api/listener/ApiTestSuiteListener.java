package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.kraken.service.QaseService;
import ru.sbermarket.qase.enums.RunResultStatus;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public final class ApiTestSuiteListener extends AllureTestNgListener {

    private final QaseService qaseService;

    public ApiTestSuiteListener() {
        String projectId = System.getProperty("qase.Project", "INAPI");
        String testRunName = System.getProperty("qase.Title", "API Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ISuite suite) {
        super.onStart(suite);
        long count;
        try(final var streamPath = Files.walk(Paths.get("build/allure-results"))) {
            count = streamPath.filter(Files::isRegularFile).count();
            if (count <= 1) {
                this.qaseService.createTestRun();
            }
        } catch (IOException e) {
            log.debug("Ошибка чтения файлов: {}", e.getMessage());
        }
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
        //Метод позволяющий наполнить фейковый контейнер, который будет размещен в блоке tearsDown
        //для завершения фейкового контейнера обходимо вызвать метод stopFake в методах onTestSuccess onTestFailure и onTestSkipped
    }
}
