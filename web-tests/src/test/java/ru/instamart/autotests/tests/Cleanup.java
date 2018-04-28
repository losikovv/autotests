package ru.instamart.autotests.tests;

import org.testng.annotations.Test;

public class Cleanup extends TestBase {

    @Test(description = "Отмена всех тестовых заказов", priority = 1)
    public void cleanupTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders();
        assertNoTestOrdersLeftActive();
    }

    @Test(description = "Удаление всех тестовых юзеров", priority = 2)
    public void cleanupTestUsers() throws Exception {
        app.getSessionHelper().deleteAllTestUsers();
        assertNoTestUsersLeft();
    }

    @Test(description = "Тест метода cleanup (отмена заказов и удаление юзеров)", priority = 3)
    public void cleanupTest() throws Exception {
        app.getSessionHelper().cleanup();
        assertNoTestOrdersLeftActive();
        assertNoTestUsersLeft();
    }

}
