package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты админки



public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel(){
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/shipments");
    }


    // TODO Кейс на недоступность админки пользователем без админ-прав


    @Test(priority = 801)
    public void resumeOrder() throws Exception {
        //TODO убрать хардкод!
        String orderNumber = "R124857258";
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // логинимся
        app.getSessionHelper().doLoginAsAdmin();
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "User wasn't successfully authorised"+"\n");
        // идем на страницу заказа в админке
        app.getNavigationHelper().getOrderAdminPage(orderNumber);
        // проверяем что заказ отменен
        Assert.assertTrue(app.getSessionHelper().isOrderCanceled(),"The order is already active" + "\n");
        // возобновляем заказ
        app.getSessionHelper().resumeOrder();
        // проверяем что заказ возобновлен
        Assert.assertFalse(app.getSessionHelper().isOrderCanceled(),"The order wasn't resumed" + "\n");
    }


    @Test(priority = 802)
    public void cancelOrder() throws Exception {
        //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами
        String orderNumber = "R124857258";
        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        // Assert order isn't cancelled
        Assert.assertFalse(app.getSessionHelper().isOrderCanceled(),
                "The order is already canceled" + "\n");

        app.getSessionHelper().cancelOrder(); // TODO перенести метов в AdministrationHelper

        // Assert order is cancelled
        Assert.assertTrue(app.getSessionHelper().isOrderCanceled(),
                "The order wasn't canceled" + "\n");
    }


    @Test(priority = 803)
    public void cancelTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders(); // TODO перенести метод в AdministrationHelper
    }

}
