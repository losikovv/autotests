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


    protected void assertPageAvailability() {
        Assert.assertFalse(app.getErrorPageHelper().its404());
        Assert.assertFalse(app.getErrorPageHelper().itsSomethingWrong());
    }
}
