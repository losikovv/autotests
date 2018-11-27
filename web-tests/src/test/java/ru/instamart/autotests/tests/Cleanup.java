package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;



// Зачистка тестовой среды после выполнения тестов



public class Cleanup extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.perform().reachAdmin(Pages.Admin.shipments());
    }


    @Test(
            description = "Отмена всех тестовых заказов",
            groups = {"acceptance"},
            priority = 901
    )
    public void cleanupTestOrders() throws Exception {
        kraken.getSessionHelper().cancelOrders(Pages.Admin.Shipments.testOrdersList());
        assertNoTestOrdersLeftActive();
    }


    @Test(
            description = "Удаление всех тестовых юзеров",
            groups = {"acceptance"},
            priority = 902
    )
    public void cleanupTestUsers() throws Exception {
        kraken.getSessionHelper().deleteUsers(Pages.Admin.Users.testUsersList());
        assertNoTestUsersLeft();
    }


    @Test( enabled = false,
            description = "Тест метода cleanup",
            groups = {"regression"},
            priority = 903
    )
    public void cleanup() throws Exception {
        kraken.getSessionHelper().cleanup();
        assertNoTestOrdersLeftActive();
        assertNoTestUsersLeft();
    }

}
