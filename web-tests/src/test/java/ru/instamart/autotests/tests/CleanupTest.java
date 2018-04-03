package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CleanupTest extends TestBase {

    @Test
    public void cleanupAutotestUsers() throws Exception {
        app.getSessionHelper().deleteAllAutotestUsers();
        app.getNavigationHelper().getAdminPage("users?q%5Bemail_cont%5D=%40example.com");
        Assert.assertFalse(app.getNavigationHelper().isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr")),"Seems like there are some autotest users left in admin panel");
    }
}
