package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;


// Basic test class



public class TestBase {

    protected static final ApplicationManager kraken = new ApplicationManager(BrowserType.FIREFOX);


    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        kraken.rise();
    }


    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        kraken.stop();
    }


    /**
     * Simply check the current page is not 404 or 500
     */
    protected void assertPageIsAvailable() throws AssertionError {
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertFalse(kraken.detect().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Current page is available (" + currentURL + ")\n");
    }

    /**
     * Get the given URL and check that reached page is equal to target one
     * Then check that reached page isn't 404 or 500
     */
    protected void assertPageIsAvailable(String URL) throws AssertionError {
        String targetURL = URL;
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(kraken.detect().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Page is available (" + currentURL + ")");
    }

    protected void assertPageIsAvailable(Pages page) throws AssertionError {
        String targetURL = kraken.get().fullBaseUrl + Pages.getPagePath();
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(kraken.detect().is404(),"Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Page is available (" + currentURL + ")");
    }

    /**
     * Simply check the current page is 404 and isn't 500
     */
    protected void assertPageIs404() throws AssertionError {
        String currentURL = kraken.perform().fetchCurrentURL();
        kraken.perform().printMessage("Checking current page " + currentURL + " is 404");
        Assert.assertTrue(kraken.detect().is404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get the given URL and check the reached page is equal to target one
     * Then check that reached page is 404 and isn't 500
     */
    protected void assertPageIs404(String URL) throws AssertionError {
        String targetURL = URL;
        kraken.perform().printMessage("Asserting page " + URL + " is 404");
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(kraken.detect().is404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
    }

    protected void assertPageIs404(Pages page) throws AssertionError {
        String targetURL = kraken.get().fullBaseUrl + Pages.getPagePath();
        kraken.perform().printMessage("Asserting page " + targetURL + " is 404");
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(kraken.detect().is404(),"Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(),"It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Check the current page isn't reachable at the moment
     */
    protected void assertPageIsUnreachable(String URL) throws AssertionError {
        String targetURL = URL;
        kraken.perform().printMessage("Checking page " + URL + " is unreachable at this moment");
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertFalse(targetURL.equalsIgnoreCase(currentURL), "It is possible to get page " + currentURL + " while it must be unreachable at this moment" + "\n");
    }

    protected void assertPageIsUnreachable(Pages page) throws AssertionError {
        String targetURL = kraken.perform().fullBaseUrl + Pages.getPagePath();
        kraken.perform().printMessage("Checking page " + targetURL + " is unreachable at this moment");
        kraken.get().url(targetURL);
        String currentURL = kraken.perform().fetchCurrentURL();
        Assert.assertFalse(targetURL.equalsIgnoreCase(currentURL), "It is possible to get page " + currentURL + " while it must be unreachable at this moment" + "\n");
    }

    protected void assertNoTestOrdersLeftActive() throws AssertionError {
        kraken.get().adminPage(Config.testOrdersList);
        Assert.assertTrue(kraken.perform().isElementPresent(By.className("no-objects-found")),"Seems like there are some test orders left active");
    }

    protected void assertNoTestUsersLeft() throws AssertionError {
        kraken.get().adminPage(Config.testUsersList);
        Assert.assertFalse(kraken.perform().isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr")),
                "Seems like there are some test users left after cleanup");
    }


    /** Проверка скачивания документации на странице деталей заказа */
    void checkOrderDocuments(){
        for(int i = 1; i <= 3; i++) {
            if(kraken.detect().orderDocument(i) != null) {
                kraken.perform().click(Elements.getLocator());
                assertPageIsAvailable();
            }
        }
    }

    /** Проверка скачивания документации заказа */
    void checkOrderDocuments(String orderNumber){
        kraken.get().page("user/orders/" + orderNumber);
        checkOrderDocuments();
    }

}
