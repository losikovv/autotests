package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.configuration.Pages;


// Basic test class



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
        //TODO добавить проверку на то что это не 502 от cloudFlare
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(app.getNavigationHelper().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getNavigationHelper().is500(),"It's something wrong on page " + currentURL + "\n");
        app.getNavigationHelper().printMessage("Current page " + currentURL + " is available");
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
        Assert.assertFalse(app.getNavigationHelper().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getNavigationHelper().is500(),"It's something wrong on page " + currentURL + "\n");
        app.getNavigationHelper().printMessage("Page " + currentURL + " is available");
    }

    protected void assertPageIsAvailable(Pages page) throws AssertionError{
        String targetURL = app.getNavigationHelper().returnBaseUrl() + Pages.getPagePath();
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(app.getNavigationHelper().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(app.getNavigationHelper().is500(),"It's something wrong on page " + currentURL + "\n");
        app.getNavigationHelper().printMessage("Page " + currentURL + " is available");
    }

    /**
     * Simply check the current page is 404 and isn't 500
     */
    protected void assertPageIs404() throws AssertionError{
        String currentURL = app.getNavigationHelper().currentURL();
        app.getNavigationHelper().printMessage("Checking current page " + currentURL + " is 404");
        Assert.assertTrue(app.getNavigationHelper().is404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(app.getNavigationHelper().is500(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get the given URL and check the reached page is equal to target one
     * Then check that reached page is 404 and isn't 500
     */
    protected void assertPageIs404(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().printMessage("Checking page " + URL + " is 404");
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(app.getNavigationHelper().is404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(app.getNavigationHelper().is500(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Check the current page isn't reachable at the moment
     */
    protected void assertPageIsUnreachable(String URL) throws AssertionError{
        String targetURL = URL;
        app.getNavigationHelper().printMessage("Checking page " + URL + " is unreachable at this moment");
        app.getNavigationHelper().getUrl(targetURL);
        String currentURL = app.getNavigationHelper().currentURL();
        Assert.assertFalse(targetURL.equalsIgnoreCase(currentURL), "It is possible to get page " + currentURL + " while it must be unreachable at this moment" + "\n");
    }

    protected void assertNoTestOrdersLeftActive() throws AssertionError {
        app.getNavigationHelper().getTestOrdersAdminPage();
        Assert.assertTrue(app.getHelper().isElementPresent(By.className("no-objects-found")),"Seems like there are some test orders left active");
    }

    protected void assertNoTestUsersLeft() throws AssertionError {
        app.getNavigationHelper().getTestUsersAdminPage();
        Assert.assertFalse(app.getHelper().isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr")), "Seems like there are some test users left");
    }

}
