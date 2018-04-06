package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Cleanup extends TestBase {

    @Test(description = "Удаление всех автотестовых юзеров")
    public void cleanupAutotestUsers() throws Exception {
        app.getSessionHelper().deleteAllAutotestUsers();
        app.getNavigationHelper().getAdminPage("users?q%5Bemail_cont%5D=%40example.com");
        Assert.assertFalse(app.getNavigationHelper().isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr")),
                "Seems like some autotest users has been left in admin panel");
    }

    //TODO добавить отмену всех тестовых заказов от пользователей @example.com
    //@Test(description = "Отмена всех тестовых заказов")
    //public void cleanupTestOrders() throws Exception {
    //}

}
