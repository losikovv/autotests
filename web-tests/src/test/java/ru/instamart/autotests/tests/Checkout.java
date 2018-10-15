package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Elements;


// Тесты чекаута



public class Checkout extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getSessionHelper().doLoginAs("admin");
        app.getNavigationHelper().getCheckoutPage();

        // если не попали в чекаут - набираем корзину и идем снова
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart();
            app.getShoppingHelper().proceedToCheckout();
        }
    }

    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты

    // TODO Тесты на добавление и изменение юрлиц


    @Test(
            description = "Тест добавления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 400
    )
    public void addPromocode(){
        app.getCheckoutHelper().addPromocode("unicorn");

        // Assert promocode is applied
        Assert.assertTrue(app.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is applied\n");
    }


    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void clearPromocode(){
        app.getCheckoutHelper().clearPromocode();

        // Assert promocode is applied
        Assert.assertFalse(app.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is cleared\n");
    }


    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 402
    )
    public void noPromocodeAddedOnCancel(){
        app.getHelper().click(Elements.Site.Checkout.addPromocodeButton());
        app.getHelper().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        app.getHelper().click(Elements.Site.Checkout.PromocodeModal.cancelButton());

        // Assert promocode is not applied
        Assert.assertFalse(app.getCheckoutHelper().isPromocodeApplied(),
                "Promocode was applied after hitting 'Cancel' button \n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 403
    )
    public void noPromocodeAddedOnClose(){
        app.getHelper().click(Elements.Site.Checkout.addPromocodeButton());
        app.getHelper().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        app.getHelper().click(Elements.Site.Checkout.PromocodeModal.closeButton());

        // Assert promocode is not applied
        Assert.assertFalse(app.getCheckoutHelper().isPromocodeApplied(),
                "Promocode was applied after hitting 'Close' button \n");
    }


    @Test(
            description = "Тест добавления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 404
    )
    public void addLoyaltyPrograms(){

        app.getCheckoutHelper().addLoyalty("mnogoru");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is applied\n");

        app.getCheckoutHelper().addLoyalty("aeroflot");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is applied\n");

        app.getCheckoutHelper().addLoyalty("svyaznoyclub");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyApplied("svyaznoyclub"),
                "Can't assert loyalty program \"svyaznoyclub\" is applied\n");
    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 405
    )
    public void selectLoyaltyProgram(){
        app.getCheckoutHelper().selectLoyalty("mnogoru");
        app.getCheckoutHelper().selectLoyalty("aeroflot");
        app.getCheckoutHelper().selectLoyalty("svyaznoyclub");
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"regression"},
            priority = 406
    )
    public void clearLoyaltyPrograms(){

        app.getCheckoutHelper().clearLoyalty("mnogoru");
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is cleared");

        app.getCheckoutHelper().clearLoyalty("aeroflot");
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is cleared");

        app.getCheckoutHelper().clearLoyalty("svyaznoyclub");
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyApplied("svyaznoyclub"),
                "Can't assert loyalty program \"svyaznoyclub\" is cleared");
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой налом",
            groups = {"acceptance","regression"},
            priority = 407
    )
    public void performCheckoutAndPayWithCash(){
        //app.getCheckoutHelper().addPromocode("unicorn");
        //app.getCheckoutHelper().addLoyalty("mnogoru");
        app.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем доки
        checkOrderDocuments();

        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 408
    )
    public void performCompleteCheckoutAndPayWithCardOnline(){
        app.getCheckoutHelper().addPromocode("unicorn");
        app.getCheckoutHelper().addLoyalty("aeroflot");
        app.getCheckoutHelper().completeCheckout("card-online");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем доки
        checkOrderDocuments();

        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 409
    )
    public void performCompleteCheckoutAndPayWithCardCourier(){
        app.getCheckoutHelper().addPromocode("unicorn");
        app.getCheckoutHelper().addLoyalty("familyteam");
        app.getCheckoutHelper().completeCheckout("card-courier");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем доки
        checkOrderDocuments();

        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 410
    )
    public void performCompleteCheckoutAndPayWithBank(){
        app.getCheckoutHelper().addPromocode("unicorn");
        app.getCheckoutHelper().addLoyalty("svyaznoyclub");
        app.getCheckoutHelper().completeCheckout("bank");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        // Проверяем доки
        checkOrderDocuments();

        app.getProfileHelper().cancelLastOrder();
    }



}
