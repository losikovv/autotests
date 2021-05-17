package ru.instamart.tests;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import ru.instamart.ui.manager.AppManager;
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

    @Test(groups = "debug")
    public void testMethodsOne() {
        AppManager.getWebDriver().get("https://google.com");
        System.out.println(((RemoteWebDriver) AppManager.getWebDriver()).getCapabilities().getVersion());
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }

    @Test(groups = "debug")
    public void testMethodsTwo() {
        AppManager.getWebDriver().get("https://google.com");
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
