package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.kraken.service.testit.TestItService;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public final class ApiTestSuiteListener extends AllureTestNgListener {

    @Override
    public void onStart(ISuite suite) {
        super.onStart(suite);
        long count;
        try(final var streamPath = Files.walk(Paths.get("build/allure-results"))) {
            count = streamPath.filter(Files::isRegularFile).count();
            if (count <= 1) {
                TestItService.INSTANCE.startTestRun();
            }
        } catch (IOException e) {
            log.debug("Ошибка чтения файлов: {}", e.getMessage());
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
        TestItService.INSTANCE.completeTestRun();
    }

    @Override
    protected void tearDown(Method method, ITestResult result) {
        TestItService.INSTANCE.updateTest(result);
        //Метод позволяющий наполнить фейковый контейнер, который будет размещен в блоке tearsDown
        //для завершения фейкового контейнера обходимо вызвать метод stopFake в методах onTestSuccess onTestFailure и onTestSkipped
    }
}
