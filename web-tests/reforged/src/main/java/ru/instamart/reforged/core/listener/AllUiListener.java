package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;

@Slf4j
public final class AllUiListener extends UiDefaultListener {

    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        super.beforeInvocation(method, testResult);
        if (method.isTestMethod())
            addCookie(method);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        fireRetryTest(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
    }
}
