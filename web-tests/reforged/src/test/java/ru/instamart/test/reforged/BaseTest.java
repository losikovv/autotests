package ru.instamart.test.reforged;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import ru.instamart.reforged.core.DoNotOpenBrowser;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.report.CustomReport;

import java.lang.reflect.Method;

@Slf4j
public class BaseTest {

    @AfterMethod(alwaysRun = true, description = "Завершение теста")
    public void tearDown(final Method method, final ITestResult result) {
        CustomReport.addSystemLog();
        if (method.isAnnotationPresent(DoNotOpenBrowser.class) || !Kraken.isAlive()) {
            return;
        }
        if (!result.isSuccess()) {
            CustomReport.addSourcePage();
            CustomReport.addBrowserLog();
            CustomReport.addCookieLog();
            CustomReport.takeScreenshot();
            CustomReport.addLocalStorage();
        }
        Kraken.closeBrowser();
    }
}
