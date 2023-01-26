package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.Cookie;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.kraken.service.testit.TestItService;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.cdp.CdpCookie;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.report.CustomReport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public class UiDefaultListener extends AllureTestNgListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        super.beforeInvocation(method, testResult);
        if (method.isTestMethod())
            addCookie(method);
    }

    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
        TestItService.INSTANCE.startTestRun();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        fireRetryTest(result);
    }

    @Override
    public void onFinish(ISuite suite) {
        TestItService.INSTANCE.completeTestRun();
        super.onFinish(suite);
    }

    protected void tearDown(final Method method, final ITestResult result) {
        if (method.isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        if (result.isSuccess()) {
            TestItService.INSTANCE.updateTest(result);
        }
        if (!result.isSuccess()) {
            TestItService.INSTANCE.updateTest(result, CustomReport.takeScreenshotFile());
            //CustomReport.addSourcePage();
            CustomReport.addBrowserLog();
            CustomReport.addCookieLog();
            CustomReport.takeScreenshot();
            CustomReport.addLocalStorage();
        }
        Kraken.closeBrowser();
        CustomReport.addSystemLog();
    }

    private void addCookie(final IInvokedMethod method) {
        try {
            final var doNotOpenBrowser = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(DoNotOpenBrowser.class);
            if (isNull(doNotOpenBrowser)) {
                final var cookieProvider = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(CookieProvider.class);
                final var cookies = getCookies(cookieProvider);
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
            log.error("FATAL: Can't add cookie for method '{}' = {}", method, e);
        }
    }

    private List<String> getCookies(final CookieProvider cookieProvider) {
        if (isNull(cookieProvider)) {
            return UiProperties.DEFAULT_COOKIES;
        }
        final var cookies = new ArrayList<>(List.of(cookieProvider.cookies()));
        if (cookieProvider.append()) {
            cookies.addAll(UiProperties.DEFAULT_COOKIES);
        }
        return cookies;
    }
}
