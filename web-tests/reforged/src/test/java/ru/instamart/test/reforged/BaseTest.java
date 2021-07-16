package ru.instamart.test.reforged;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.report.CustomReport;
import ru.instamart.reforged.core.service.KrakenDriver;

@Slf4j
public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void captureStart() {
        LogAttachmentHelper.start();
        log.info("Browser session id: {}", KrakenDriver.getSessionId());
    }

    @AfterMethod(alwaysRun = true, description = "Завершение теста")
    public void tearDown(final ITestResult result) {
        CustomReport.addSystemLog();
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
