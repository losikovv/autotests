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
     * Check that current page is not 404 or 500
     */
    protected void assertPageIsAvailable() throws AssertionError{
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(app.getErrorPageHelper().its404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getErrorPageHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Check that current page is not 404 or 500
     */
    protected void assertPageIsUnreachable(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(targetURL.equalsIgnoreCase(currentURL), "It is possible to get page " + currentURL + " while it has to be unreachable" + "\n");
    }

    /**
     * Get given URL and check that reached page is equal to target one
     * Then check that reached page is not 404 or 500
     */
    protected void getPageAndAssertAvailability(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target " + targetURL + "\n");
        Assert.assertFalse(app.getErrorPageHelper().its404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getErrorPageHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get given URL and check that reached page is equal to target one
     * Then check that reached page is 404
     */
    protected void getPageAndAssertIts404(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target " + targetURL + "\n");
        Assert.assertTrue(app.getErrorPageHelper().its404(),"Page " + currentURL + " is not 404" + "\n");
        Assert.assertFalse(app.getErrorPageHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

}
