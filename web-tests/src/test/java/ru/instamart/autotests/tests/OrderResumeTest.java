package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class OrderResumeTest extends TestBase {
    @Test
    public void resumeOrder() throws Exception {
        //TODO убрать хардкод!
        String orderNumber = "R124857258";
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // логинимся
        app.getSessionHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7", null));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(), "User wasn't successfully authorised"+"\n");
        // идем на страницу заказа в админке
        app.getNavigationHelper().getOrderPageAdmin(orderNumber);
        // проверяем что заказ отменен
        Assert.assertTrue(app.getShoppingHelper().orderIsCanceled(),"The order is already active" + "\n");
        // возобновляем заказ
        app.getShoppingHelper().resumeOrderFromAdmin();
        // TODO добавить задержку в 5 сек иначе заказ часто не успевает возобновиться и тест падает
        // проверяем что заказ возобновлен
        Assert.assertFalse(app.getShoppingHelper().orderIsCanceled(),"The order wasn't resumed" + "\n");
    }
}
