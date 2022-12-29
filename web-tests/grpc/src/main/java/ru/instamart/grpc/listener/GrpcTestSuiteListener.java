package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.kraken.service.testit.TestItService;

import java.lang.reflect.Method;

@Slf4j
public final class GrpcTestSuiteListener extends AllureTestNgListener {

    @Override
    public void onStart(ISuite suite) {
        super.onStart(suite);
        TestItService.INSTANCE.startTestRun();
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
        TestItService.INSTANCE.completeTestRun();
    }

    @Override
    protected void tearDown(Method method, ITestResult result) {
        TestItService.INSTANCE.updateTest(result);
    }
}
