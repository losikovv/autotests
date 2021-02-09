package ru.instamart.tests;

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
    public void testMethodsOne() throws InterruptedException {
        kraken.getWebDriver().get("https://google.com");
        Thread.sleep(15000);
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }

    @Test(groups = "Foo")
    public void testMethodsTwo() throws InterruptedException {
        kraken.getWebDriver().get("https://google.com");
        Thread.sleep(15000);
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }

    @AfterSuite(groups = {
            "testing","sbermarket-Ui-smoke","MRAutoCheck","sbermarket-acceptance","sbermarket-regression",
            "metro-smoke","metro-acceptance", "Foo", "metro-regression","admin-ui-smoke"},
            description = "Выпускаем Кракена", alwaysRun = true)
    public void end() {
        long id = Thread.currentThread().getId();
        System.out.println("Suite After. Thread id is: " + id);
    }
}
