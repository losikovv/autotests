package ru.instamart.autotests.tests;

import org.testng.annotations.Test;



    // Зачистка тестовой среды после тестов



public class Cleanup extends TestBase {


    @Test(
            description = "Отмена всех тестовых заказов",
            groups = {"acceptance","regression"},
            priority = 901
    )
    public void cleanupTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders();
        assertNoTestOrdersLeftActive();
    }


    @Test(
            description = "Удаление всех тестовых юзеров",
            groups = {"acceptance","regression"},
            priority = 902
    )
    public void cleanupTestUsers() throws Exception {
        app.getSessionHelper().deleteAllTestUsers();
        assertNoTestUsersLeft();
    }


    @Test(
            description = "Тест метода cleanup",
            groups = {"regression"},
            priority = 903
    )
    public void cleanup() throws Exception {
        app.getSessionHelper().cleanup();
        assertNoTestOrdersLeftActive();
        assertNoTestUsersLeft();
    }

}
