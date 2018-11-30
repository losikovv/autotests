package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

import static ru.instamart.autotests.application.Elements.*;


// Тесты чекаута


public class Checkout extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs("admin");
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
        kraken.perform().reachCheckout();
        kraken.checkout().addPromocode("unicorn");

        // Assert promocode is applied
        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is applied\n");
    }


    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void clearPromocode(){
        kraken.perform().reachCheckout();
        kraken.checkout().clearPromocode();

        // Assert promocode is applied
        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "Can't assert promocode is cleared\n");
    }


    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 402
    )
    public void noPromocodeAddedOnCancel(){
        kraken.perform().reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.cancelButton());

        // Assert promocode is not applied
        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "Promocode was applied after hitting 'Cancel' button \n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 403
    )
    public void noPromocodeAddedOnClose(){
        kraken.perform().reachCheckout();
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.closeButton());

        // Assert promocode is not applied
        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "Promocode was applied after hitting 'Close' button \n");
    }


    @Test(
            description = "Тест добавления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 404
    )
    public void addLoyaltyPrograms(){
        kraken.perform().reachCheckout();
        kraken.checkout().addLoyalty("mnogoru");
        Assert.assertTrue(kraken.detect().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is applied\n");

        kraken.checkout().addLoyalty("aeroflot");
        Assert.assertTrue(kraken.detect().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is applied\n");

    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 405
    )
    public void selectLoyaltyProgram(){
        kraken.perform().reachCheckout();
        kraken.checkout().selectLoyalty("mnogoru");
        kraken.checkout().selectLoyalty("aeroflot");
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"regression"},
            priority = 406
    )
    public void clearLoyaltyPrograms(){
        kraken.perform().reachCheckout();
        kraken.checkout().clearLoyalty("mnogoru");
        Assert.assertFalse(kraken.detect().isLoyaltyApplied("mnogoru"),
                "Can't assert loyalty program \"mnogoru\" is cleared");

        kraken.checkout().clearLoyalty("aeroflot");
        Assert.assertFalse(kraken.detect().isLoyaltyApplied("aeroflot"),
                "Can't assert loyalty program \"aeroflot\" is cleared");
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой налом",
            groups = {"acceptance","regression"},
            priority = 407
    )
    public void performCheckoutAndPayWithCash(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete();

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой онлайн",
            groups = {"regression"},
            priority = 408
    )
    public void performCompleteCheckoutAndPayWithCardOnline(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete("card-online");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой картой курьеру",
            groups = {"regression"},
            priority = 409
    )
    public void performCompleteCheckoutAndPayWithCardCourier(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete("card-courier");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест полного оформления заказа с оплатой банковским переводом",
            groups = {"regression"},
            priority = 410
    )
    public void performCompleteCheckoutAndPayWithBank(){
        kraken.perform().reachCheckout();
        kraken.checkout().complete("bank");

        // Проверяем что заказ оформился и активен
        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Can't assert the order is sent & active, check manually\n");

        kraken.perform().checkOrderDocuments();
        assertPageIsAvailable();

        kraken.perform().cancelLastOrder();
    }


    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 411
    )
    public void checkMetroDeliveryPriceDiscount() throws Exception {
        kraken.perform().dropCart();

        kraken.get().page(Pages.Site.Catalog.priceyItems());
        kraken.shopping().grabCart();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().fillAllFields();

        Assert.assertEquals(kraken.grab().roundedPrice(Site.Checkout.deliveryPrice()), Config.MetroHighDeliveryPrice,
                "Delivery price in checkout is not correct, check manually \n");

        kraken.get().page(Pages.Site.Catalog.priceyItems());
        kraken.shopping().grabCart(5000);
        kraken.shopping().proceedToCheckout();
        Assert.assertEquals(kraken.grab().roundedPrice(Site.Checkout.deliveryPrice()), Config.MetroMediumDeliveryPrice,
                "Delivery price in checkout is not correct, check manually \n" );

        kraken.get().page(Pages.Site.Catalog.priceyItems());
        kraken.shopping().grabCart(10000);
        kraken.shopping().proceedToCheckout();
        Assert.assertEquals(kraken.grab().roundedPrice(Site.Checkout.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "Delivery price in checkout is not correct, check manually \n" );

        kraken.checkout().complete();

        Assert.assertEquals(kraken.grab().roundedPrice(Site.OrderDetailsPage.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "Delivery price is not correct in order details, check manually \n" );

        kraken.perform().cancelLastOrder();
    }

}
