package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Pages;


// Зачистка тестовой среды после выполнения тестов


public class Cleanup extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.perform().reachAdmin(Pages.Admin.shipments());
    }


    @Test( enabled = false,
            description = "Тест метода cleanup",
            groups = {"regression"},
            priority = 900
    )
    public void cleanup() throws Exception {
        kraken.cleanup().all();
        assertNoTestOrdersLeftActive();
        assertNoTestUsersLeft();
    }


    @Test(
            description = "Отмена всех тестовых заказов",
            groups = {"acceptance"},
            priority = 901
    )
    public void cleanupTestOrders() throws Exception {
        kraken.cleanup().orders(Config.testOrdersList);
        assertNoTestOrdersLeftActive();
    }


    @Test(
            description = "Удаление всех тестовых юзеров",
            groups = {"acceptance"},
            priority = 902
    )
    public void cleanupTestUsers() throws Exception {
        kraken.cleanup().users(Config.testUsersList);
        assertNoTestUsersLeft();
    }

}
