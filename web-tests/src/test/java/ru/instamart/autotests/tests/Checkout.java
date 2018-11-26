package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Pages;


// Тесты чекаута



public class Checkout extends TestBase {

    private void reachCheckout() {
        kraken.getNavigationHelper().getCheckoutPage();
        if(!kraken.getCheckoutHelper().isOnCheckout()){
            kraken.getShoppingHelper().grabCart();
            kraken.getShoppingHelper().proceedToCheckout();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        kraken.getNavigationHelper().getRetailerPage("metro");
        kraken.getSessionHelper().doLoginAs("admin");
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
        kraken.getCheckoutHelper().addPromocode("unicorn");

        // Assert promocode is applied
        Assert.assertTrue(kraken.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is applied\n");
    }


    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void clearPromocode(){
        reachCheckout();
        kraken.getCheckoutHelper().clearPromocode();

        // Assert promocode is applied
        Assert.assertFalse(kraken.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is cleared\n");
    }


    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 402
    )
    public void noPromocodeAddedOnCancel(){
        reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.cancelButton());

        // Assert promocode is not applied
        Assert.assertFalse(kraken.getCheckoutHelper().isPromocodeApplied(),
                "Promocode was applied after hitting 'Cancel' button \n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 403
    )
    public void noPromocodeAddedOnClose(){
        reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.closeButton());

        // Assert promocode is not applied
        Assert.assertFalse(kraken.getCheckoutHelper().isPromocodeApplied(),
                "Promocode was applied after hitting 'Close' button \n");
    }


    @Test(
            description = "Тест добавления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 404
    )
    public void addLoyaltyPrograms(){
        reachCheckout();
        kraken.getCheckoutHelper().addLoyalty("mnogoru");
        Assert.assertTrue(kraken.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is applied\n");

        kraken.getCheckoutHelper().addLoyalty("aeroflot");
        Assert.assertTrue(kraken.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is applied\n");

    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 405
    )
    public void selectLoyaltyProgram(){
        reachCheckout();
        kraken.getCheckoutHelper().selectLoyalty("mnogoru");
        kraken.getCheckoutHelper().selectLoyalty("aeroflot");
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"regression"},
            priority = 406
    )
    public void clearLoyaltyPrograms(){
        reachCheckout();
        kraken.getCheckoutHelper().clearLoyalty("mnogoru");
        Assert.assertFalse(kraken.getCheckoutHelper().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is cleared");

        kraken.getCheckoutHelper().clearLoyalty("aeroflot");
        Assert.assertFalse(kraken.getCheckoutHelper().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is cleared");
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой налом",
            groups = {"acceptance","regression"},
            priority = 407
    )
    public void performCheckoutAndPayWithCash(){
        reachCheckout();
        kraken.getCheckoutHelper().completeCheckout();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        kraken.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 408
    )
    public void performCompleteCheckoutAndPayWithCardOnline(){
        reachCheckout();
        kraken.getCheckoutHelper().completeCheckout("card-online");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        kraken.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 409
    )
    public void performCompleteCheckoutAndPayWithCardCourier(){
        reachCheckout();
        kraken.getCheckoutHelper().completeCheckout("card-courier");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        kraken.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 410
    )
    public void performCompleteCheckoutAndPayWithBank(){
        reachCheckout();
        kraken.getCheckoutHelper().completeCheckout("bank");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.getProfileHelper().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        checkOrderDocuments();
        kraken.getProfileHelper().cancelLastOrder();
    }


    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 411
    )
    public void checkMetroDeliveryPriceDiscount() {
        kraken.getShoppingHelper().dropCart();

        kraken.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        kraken.getShoppingHelper().grabCart();
        kraken.getShoppingHelper().proceedToCheckout();
        kraken.getCheckoutHelper().fillCheckout();
        Assert.assertTrue(kraken.getCheckoutHelper().checkDeliveryPrice(299),
                "Delivery price in checkout is not correct, check manually \n" );

        kraken.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        kraken.getShoppingHelper().grabCart(5000);
        kraken.getShoppingHelper().proceedToCheckout();
        Assert.assertTrue(kraken.getCheckoutHelper().checkDeliveryPrice(199),
                "Delivery price in checkout is not correct, check manually \n" );

        kraken.getNavigationHelper().get(Pages.Site.Catalog.priceyItems());
        kraken.getShoppingHelper().grabCart(10000);
        kraken.getShoppingHelper().proceedToCheckout();
        Assert.assertTrue(kraken.getCheckoutHelper().checkDeliveryPrice(99),
                "Delivery price in checkout is not correct, check manually \n" );

        kraken.getCheckoutHelper().completeCheckout();
        Assert.assertTrue(kraken.getProfileHelper().checkDeliveryPrice(99),
                "Delivery price is not correct in order details, check manually \n" );

        kraken.getProfileHelper().cancelLastOrder();
    }



}
