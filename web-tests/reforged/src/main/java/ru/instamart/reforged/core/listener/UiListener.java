package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.Cookie;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.instamart.kraken.retry.RetryAnalyzer;
import ru.instamart.kraken.service.QaseService;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.KrakenParams;
import ru.instamart.reforged.core.cdp.CdpCookie;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.listener.allure.AllureTestNgListener;
import ru.instamart.reforged.core.report.CustomReport;
import ru.sbermarket.qase.enums.RunResultStatus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public final class UiListener extends AllureTestNgListener {

    private final QaseService qaseService;

    public UiListener() {
        super();
        final var projectId = System.getProperty("qase.Project", "STF");
        final var testRunName = System.getProperty("qase.Title", "UI Test Run");
        this.qaseService = new QaseService(projectId, testRunName);
    }

    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
        this.qaseService.createTestRun();
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
        this.qaseService.sendResult(result, RunResultStatus.passed);
        stopFake(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        stopFake(result);
        if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        fireRetryTest(result);
        this.qaseService.sendResult(result, RunResultStatus.failed, qaseService.uploadScreenshot(CustomReport.takeScreenshotFile()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        this.qaseService.sendResult(result, RunResultStatus.blocked);
        stopFake(result);
    }

    @Override
    public void onFinish(ISuite suite) {
        super.onFinish(suite);
        try {
            this.qaseService.completeTestRun();
        } catch (Exception e) {
            log.error("FATAL: something wrong when try to finish test run", e);
        }
    }

    private void fireRetryTest(final ITestResult result) {
        final var retryAnalyzer = result.getMethod().getRetryAnalyzer(result);
        //TODO: Wait Pattern Matching for instanceof Java 14++
        if (retryAnalyzer instanceof RetryAnalyzer) {
            final var retry = (RetryAnalyzer) retryAnalyzer;
            retry.retry(result);
        }
    }

    /**
     * Создание фейкового контейнера, в который кладём необходимую дополнительную информацию о браузере
     * и завершаем сессию.
     */
    public void stopFake(final ITestResult testResult) {
        tearDownFixtureStarted(testResult.getMethod());
        tearDown(testResult.getMethod().getConstructorOrMethod().getMethod(), testResult);
        tearDownFixtureStop();
        stopContainer(testResult.getMethod());
    }

    private void tearDown(final Method method, final ITestResult result) {
        CustomReport.addSystemLog();
        if (method.isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        if (!result.isSuccess()) {
            //CustomReport.addSourcePage();
            CustomReport.addBrowserLog();
            CustomReport.addCookieLog();
            CustomReport.takeScreenshot(getParamsOrNull(method));
            CustomReport.addLocalStorage();
        }
        Kraken.closeBrowser();
    }

    private KrakenParams getParamsOrNull(final Method method) {
        if (method.isAnnotationPresent(KrakenParams.class)) {
            return method.getAnnotation(KrakenParams.class);
        }
        return null;
    }

    private void addCookie(final IInvokedMethod method) {
        try {
            final var doNotOpenBrowser = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(DoNotOpenBrowser.class);
            if (isNull(doNotOpenBrowser)) {
                final var customCookies = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(CookieProvider.class);
                final var cookies = isNull(customCookies) ? UiProperties.DEFAULT_COOKIES : Arrays.asList(customCookies.cookies());
                final var fields = FieldUtils.getAllFieldsList(CookieFactory.class)
                        .stream()
                        .filter(f -> cookies.contains(f.getName()))
                        .map(r -> {
                            try {
                                return (Cookie) FieldUtils.readField(r, CookieFactory.class, true);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toUnmodifiableSet());
                CdpCookie.addCookies(fields);
            }
        } catch (Exception e) {
            log.error("FATAL: Can't add cookie for method {}", method);
        }
    }
}
