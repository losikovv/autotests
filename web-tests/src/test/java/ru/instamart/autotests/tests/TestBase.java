package ru.instamart.autotests.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.instamart.autotests.appmanager.ApplicationManager;



    // Test base class



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
     * Simply check the current page is not 404 or 500
     */
    protected void assertPageIsAvailable() throws AssertionError{
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(app.getNavigationHelper().its404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getNavigationHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get the given URL and check that reached page is equal to target one
     * Then check that reached page isn't 404 or 500
     */
    protected void assertPageIsAvailable(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(app.getNavigationHelper().its404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getNavigationHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Simply check the current page is 404 and isn't 500
     */
    protected void assertPageIs404() throws AssertionError{
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(app.getNavigationHelper().its404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(app.getNavigationHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get the given URL and check the reached page is equal to target one
     * Then check that reached page is 404 and isn't 500
     */
    protected void assertPageIs404(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(app.getNavigationHelper().its404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(app.getNavigationHelper().itsSomethingWrong(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Check the current page isn't reachable at the moment
     */
    protected void assertPageIsUnreachable(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(targetURL.equalsIgnoreCase(currentURL), "It is possible to get page " + currentURL + " while it must be unreachable at this moment" + "\n");
    }

}
