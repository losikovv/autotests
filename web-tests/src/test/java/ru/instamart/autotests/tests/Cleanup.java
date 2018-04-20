package ru.instamart.autotests.tests;

import org.testng.annotations.Test;

public class Cleanup extends TestBase {

    @Test(description = "Отмена всех тестовых заказов")
    public void cleanupTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders();
        assertNoTestOrdersLeftActive();
    }

    @Test(description = "Удаление всех тестовых юзеров",priority = 1)
    public void cleanupTestUsers() throws Exception {
        app.getSessionHelper().deleteAllTestUsers();
        assertNoTestUsersLeft();
    }

}
