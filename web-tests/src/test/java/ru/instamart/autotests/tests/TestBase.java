package ru.instamart.autotests.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.application.Pages;


// Basic test class


public class TestBase {

    protected static final ApplicationManager kraken = new ApplicationManager(BrowserType.CHROME);


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
        String currentURL = kraken.grab().currentURL();
        Assert.assertFalse(kraken.detect().is404(), "Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Current page is available (" + currentURL + ")\n");
    }

    /**
     * Get the given URL and check that reached page is equal to target one
     * Then check that reached page isn't 404 or 500
     */
    protected void assertPageIsAvailable(String URL) throws AssertionError {
        String targetURL = URL;
        kraken.get().url(targetURL);
        String currentURL = kraken.grab().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(kraken.detect().is404(), "Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Page is available (" + currentURL + ")");
    }

    protected void assertPageIsAvailable(Pages page) throws AssertionError {
        String targetURL = kraken.get().fullBaseUrl + Pages.getPagePath();
        kraken.get().url(targetURL);
        String currentURL = kraken.grab().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL " + targetURL + "\n");
        Assert.assertFalse(kraken.detect().is404(), "Page " + currentURL + " is 404" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
        kraken.perform().printMessage("Page is available (" + currentURL + ")");
    }

    /**
     * Simply check the current page is 404 and isn't 500
     */
    protected void assertPageIs404() throws AssertionError {
        String currentURL = kraken.grab().currentURL();
        kraken.perform().printMessage("Checking current page " + currentURL + " is 404");
        Assert.assertTrue(kraken.detect().is404(), "Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
    }

    /**
     * Get the given URL and check the reached page is equal to target one
     * Then check that reached page is 404 and isn't 500
     */
    protected void assertPageIs404(String URL) throws AssertionError {
        // TODO использовать get метод
        String targetURL = URL;
        kraken.perform().printMessage("Asserting page " + URL + " is 404");
        kraken.get().url(targetURL);
        String currentURL = kraken.grab().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(kraken.detect().is404(), "Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
    }

    protected void assertPageIs404(Pages page) throws AssertionError {
        String targetURL = kraken.get().fullBaseUrl + Pages.getPagePath();
        kraken.perform().printMessage("Asserting page " + targetURL + " is 404");
        kraken.get().url(targetURL);
        String currentURL = kraken.grab().currentURL();
        Assert.assertTrue(targetURL.equalsIgnoreCase(currentURL), "Reached URL " + currentURL + " instead of target URL" + targetURL + "\n");
        Assert.assertTrue(kraken.detect().is404(), "Page " + currentURL + " must be 404, but it's not" + "\n");
        Assert.assertFalse(kraken.detect().is500(), "It's something wrong on page " + currentURL + "\n");
    }

    /** Проверка недоступности страницы для перехода */
    void assertPageIsUnavailable(Pages page) throws AssertionError {
        assertPageIsUnavailable(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    void assertPageIsUnavailable(String URL) throws AssertionError {
        kraken.perform().printMessage("Проверяем недоступность перехода на страницу " + URL);
        kraken.get().url(URL);
        String currentURL = kraken.grab().currentURL();
        Assert.assertFalse(currentURL.equalsIgnoreCase(URL),
                "Можно попасть на страницу " + currentURL + " по прямой ссылке\n");
    }
}
