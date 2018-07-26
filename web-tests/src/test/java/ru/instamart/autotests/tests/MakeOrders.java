package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты заказов



public class MakeOrders extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparing() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");

        // авторизуемся, если нужно
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
    }


    @Test(
            description = "Тестовый заказ в Казани",
            groups = {"acceptance","regression"},
            priority = 450
    )
    public void Kazan(){

        app.getShoppingHelper().changeShippingAddress("Казань, ул Мулланура Вахитова, д 10");

        // идем в чекаут, при необходимости набирая корзину
        app.getNavigationHelper().getCheckoutPage();
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCartWithMinimalOrderSum();
            app.getShoppingHelper().proceedToCheckout();
        }

        app.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");
    }


    @AfterMethod(alwaysRun = true)
    public void cancelLastOrder()throws Exception {
        app.getProfileHelper().cancelLastOrder();
        app.getNavigationHelper().goHome();
        app.getShoppingHelper().changeShippingAddress("Москва, ул Просторная, д 77");
    }

}
