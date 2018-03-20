package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class OrderCancellationTest extends TestBase {

    @Test
    public void cancelOrder() throws Exception {
        //TODO убрать хардкод!
        String orderNumber = "R124857258";
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // логинимся
        app.getSessionHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised(), "User wasn't successfully authorised"+"\n");
        // get order page in admin
        app.getNavigationHelper().getOrderPageAdmin(orderNumber);
        // проверяем что заказ еще не отменен
        Assert.assertFalse(app.getShoppingHelper().orderIsCanceled(),"The order is already canceled" + "\n");
        // отменяем заказ через админку
        app.getShoppingHelper().cancelOrder();
        // проверяем что заказ отменен
        Assert.assertTrue(app.getShoppingHelper().orderIsCanceled(),"The order wasn't canceled" + "\n");
    }
}
