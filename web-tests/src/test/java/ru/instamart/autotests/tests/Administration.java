package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;



    // Тесты админки



public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel(){
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/shipments"); // TODO параметризовать окружение
    }


    @Test(
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 700
    )
    public void adminPanelUnreacheableWithoutPrivileges() throws Exception {
        app.getSessionHelper().doLogout();
        app.getSessionHelper().doLogin(new UserData("instatestuser@yandex.ru","instamart", null));

        // Assert admin panel is unreacheable
        assertPageIsUnreachable("https://instamart.ru/admin/shipments"); // TODO параметризовать окружение

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест возобновления заказа через админку",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void resumeOrder() throws Exception {

        String orderNumber = "R124857258"; //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами
        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        // Assert order is cancelled
        Assert.assertTrue(app.getAdministrationHelper().isOrderCanceled(),
                "The order is already active" + "\n");

        app.getAdministrationHelper().resumeOrder();

        // Assert order isn't cancelled
        Assert.assertFalse(app.getAdministrationHelper().isOrderCanceled(),
                "Can't approve the order was resumed, check manually\n");
    }


    @Test(
            description = "Тест отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void cancelOrder() throws Exception {

        String orderNumber = "R124857258"; // TODO заменить на номер заказа тестового пользователя
        //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами

        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        Assert.assertFalse(app.getAdministrationHelper().isOrderCanceled(),
                "The order is already canceled" + "\n");

        app.getSessionHelper().cancelOrder(); // TODO перенести метод в AdministrationHelper

        Assert.assertTrue(app.getAdministrationHelper().isOrderCanceled(),
                "Can't approve the order was canceled, check manually\n");
    }


    @Test(priority = 703)
    public void cancelTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders(); // TODO перенести метод в AdministrationHelper
    }

}
