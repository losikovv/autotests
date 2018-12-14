package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;


// Зачистка тестовой среды после выполнения тестов


public class Cleanup extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.perform().reachAdmin();
    }


    @Test(
            description = "Тест метода cleanup",
            groups = {"acceptance"},
            priority = 10000
    )
    public void cleanup() throws Exception {
        kraken.cleanup().all();

        kraken.get().adminPage(Config.testOrdersList);
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Shipments.emptyListPlaceholder()),
                "Отменились не все тестовые заказы\n");

        kraken.get().adminPage(Config.testUsersList);
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Users.userlistFirstRow()),
                "Удалились не все тестовые юзеры\n");

    }


    @Test(
            description = "Отмена всех тестовых заказов",
            groups = {"regression"},
            priority = 10001
    )
    public void cleanupTestOrders() throws Exception {
        kraken.cleanup().orders(Config.testOrdersList);
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Shipments.emptyListPlaceholder()),
                "Отменились не все тестовые заказы\n");
    }


    @Test(
            description = "Удаление всех тестовых юзеров",
            groups = {"regression"},
            priority = 10002
    )
    public void cleanupTestUsers() throws Exception {
        kraken.cleanup().users(Config.testUsersList);
        Assert.assertFalse(kraken.detect().element(Elements.Admin.Users.userlistFirstRow()),
                "Удалились не все тестовые юзеры\n");
    }

}
