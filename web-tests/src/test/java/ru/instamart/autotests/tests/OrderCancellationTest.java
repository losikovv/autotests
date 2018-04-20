package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderCancellationTest extends TestBase {

    @Test
    public void cancelTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders();
    }

    /*
    @Test
    public void cancelOrder() throws Exception {
        //TODO убрать хардкод!
        String orderNumber = "R124857258";
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // логинимся
        app.getSessionHelper().doLoginAsAdmin();
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "User wasn't successfully authorised"+"\n");
        // get order page in admin
        app.getNavigationHelper().getOrderAdminPage(orderNumber);
        // проверяем что заказ еще не отменен
        Assert.assertFalse(app.getSessionHelper().isOrderCanceled(),"The order is already canceled" + "\n");
        // отменяем заказ через админку
        app.getSessionHelper().cancelOrder();
        // проверяем что заказ отменен
        Assert.assertTrue(app.getSessionHelper().isOrderCanceled(),"The order wasn't canceled" + "\n");
    }
    */
}
