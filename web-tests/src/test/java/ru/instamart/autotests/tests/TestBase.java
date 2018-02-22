package ru.instamart.autotests.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.instamart.autotests.appmanager.ApplicationManager;

// базовый класс тестов
public class TestBase {

    protected final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    /**
     * Method gets given URL and checks that reached URL is equal to target one
     * Also method checks that page on reached URL is not 404 or 500
     */

    protected void assertPageAvailability(String URL) {
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Target URL " + targetURL + " was not reached! Reached URL is " + currentURL + "\n");
        Assert.assertFalse(app.getErrorPageHelper().its404(),"It's 404 on page " + currentURL+ "\n");
        Assert.assertFalse(app.getErrorPageHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

}
