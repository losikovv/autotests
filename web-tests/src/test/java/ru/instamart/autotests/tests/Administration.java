package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты админки



public class Administration extends TestBase {


    // TODO Кейс на недоступность админки пользователем без админ-прав


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel(){
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/shipments");
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
        Assert.assertTrue(app.getSessionHelper().isOrderCanceled(),
                "The order is already active" + "\n");

        app.getSessionHelper().resumeOrder();

        // Assert order isn't cancelled
        Assert.assertFalse(app.getSessionHelper().isOrderCanceled(),
                "The order wasn't resumed" + "\n");
    }


    @Test(
            description = "Тест отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void cancelOrder() throws Exception {

        String orderNumber = "R124857258"; //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами
        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        // Assert order isn't cancelled
        Assert.assertFalse(app.getSessionHelper().isOrderCanceled(),
                "The order is already canceled" + "\n");

        app.getSessionHelper().cancelOrder(); // TODO перенести метов в AdministrationHelper

        // Assert order is cancelled
        Assert.assertTrue(app.getSessionHelper().isOrderCanceled(),
                "The order wasn't canceled" + "\n");
    }


    @Test(priority = 703)
    public void cancelTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders(); // TODO перенести метод в AdministrationHelper
    }

}
