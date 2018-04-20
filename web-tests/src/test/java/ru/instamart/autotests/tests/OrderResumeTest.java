package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderResumeTest extends TestBase {
    @Test
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
}
