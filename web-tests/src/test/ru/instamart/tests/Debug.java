package ru.instamart.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.tests.ui.TestBase;

public class Debug extends TestBase {

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        long id = Thread.currentThread().getId();
        System.out.println("Test Before. Thread id is: " + id);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
    }

    @Test(groups = "Foo")
    public void testMethodsOne() {
        kraken.getWebDriver().get("https://google.com");
        Assert.assertTrue(false);
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }

    @Test(groups = "Foo")
    public void testMethodsTwo() {
        kraken.getWebDriver().get("https://google.com");
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }

    @AfterSuite
    public void end() {
        long id = Thread.currentThread().getId();
        System.out.println("Suite After. Thread id is: " + id);
    }
}
