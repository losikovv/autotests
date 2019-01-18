package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;


// Тесты цен доставки


public class DeliveryPrices extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().page("metro");
        kraken.perform().loginAs("admin");
    }


    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 801
    )
    public void successCheckMetroDeliveryPriceDiscount() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().dropCart();

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().fillAllFields();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Site.Checkout.deliveryPrice()), Config.MetroHighDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при малой корзине");

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems(5000);
        kraken.shopping().proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Site.Checkout.deliveryPrice()), Config.MetroMediumDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при средней корзине" );

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems(10000);
        kraken.shopping().proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Site.Checkout.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при большой корзине" );

        kraken.checkout().complete();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Site.OrderDetailsPage.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки на странице заказа" );

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }
}
