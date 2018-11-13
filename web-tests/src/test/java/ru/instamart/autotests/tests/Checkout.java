package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Pages;


// Тесты чекаута



public class Checkout extends TestBase {

    private void reachCheckout() {
        app.getNavigationHelper().getCheckoutPage();
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().grabCart();
            app.getShoppingHelper().proceedToCheckout();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getSessionHelper().doLoginAs("admin");
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
        reachCheckout();
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
        reachCheckout();
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
        reachCheckout();
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
        reachCheckout();
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
        reachCheckout();
        app.getCheckoutHelper().addLoyalty("mnogoru");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is applied\n");

        app.getCheckoutHelper().addLoyalty("aeroflot");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is applied\n");

    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 405
    )
    public void selectLoyaltyProgram(){
        reachCheckout();
        app.getCheckoutHelper().selectLoyalty("mnogoru");
        app.getCheckoutHelper().selectLoyalty("aeroflot");
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"regression"},
            priority = 406
    )
    public void clearLoyaltyPrograms(){
        reachCheckout();
        app.getCheckoutHelper().clearLoyalty("mnogoru");
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is cleared");

        app.getCheckoutHelper().clearLoyalty("aeroflot");
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is cleared");
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой налом",
            groups = {"acceptance","regression"},
            priority = 407
    )
    public void performCheckoutAndPayWithCash(){
        reachCheckout();
        app.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 408
    )
    public void performCompleteCheckoutAndPayWithCardOnline(){
        reachCheckout();
        app.getCheckoutHelper().completeCheckout("card-online");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 409
    )
    public void performCompleteCheckoutAndPayWithCardCourier(){
        reachCheckout();
        app.getCheckoutHelper().completeCheckout("card-courier");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 410
    )
    public void performCompleteCheckoutAndPayWithBank(){
        reachCheckout();
        app.getCheckoutHelper().completeCheckout("bank");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(app.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        app.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 411
    )
    public void checkMetroDeliveryPriceDiscount() {
        app.getShoppingHelper().dropCart();

        app.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        app.getShoppingHelper().grabCart();
        app.getShoppingHelper().proceedToCheckout();
        app.getCheckoutHelper().fillCheckout();
        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(299),
                "Delivery price in checkout is not correct, check manually \n" );

        app.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        app.getShoppingHelper().grabCart(5000);
        app.getShoppingHelper().proceedToCheckout();
        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(199),
                "Delivery price in checkout is not correct, check manually \n" );

        app.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        app.getShoppingHelper().grabCart(10000);
        app.getShoppingHelper().proceedToCheckout();
        Assert.assertTrue(app.getCheckoutHelper().checkDeliveryPrice(99),
                "Delivery price in checkout is not correct, check manually \n" );

        app.getCheckoutHelper().completeCheckout();
        Assert.assertTrue(app.getProfileHelper().checkDeliveryPrice(99),
                "Delivery price is not correct in order details, check manually \n" );

        app.getProfileHelper().cancelLastOrder();
    }



}
