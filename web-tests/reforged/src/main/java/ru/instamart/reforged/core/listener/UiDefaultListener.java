package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.Cookie;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import ru.instamart.kraken.listener.AllureTestNgListener;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.cdp.CdpCookie;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.report.CustomReport;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
public class UiDefaultListener extends AllureTestNgListener {

    protected void tearDown(final Method method, final ITestResult result) {
        CustomReport.addSystemLog();
        if (method.isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        if (!result.isSuccess()) {
            //CustomReport.addSourcePage();
            CustomReport.addBrowserLog();
            CustomReport.addCookieLog();
            CustomReport.takeScreenshot();
            CustomReport.addLocalStorage();
        }
        Kraken.closeBrowser();
    }

    protected void addCookie(final IInvokedMethod method) {
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
            log.error("FATAL: Can't add cookie for method '{}' = {}", method, e);
        }
    }
}
