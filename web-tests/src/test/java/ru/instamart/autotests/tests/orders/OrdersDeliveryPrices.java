package ru.instamart.autotests.tests.orders;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class OrdersDeliveryPrices extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
        User.Do.loginAs(session.user);
    }

    // TODO переделать тесты
    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 801
    )
    public void successCheckMetroDeliveryPriceDiscount() {
        SoftAssert softAssert = new SoftAssert();
        Shop.Cart.drop();

        kraken.get().baseUrl();
        Shop.Search.item("Haagen");
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().fillOrderDetails();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.TestVariables.DeliveryPrices.MetroHighDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при малой корзине");

        kraken.get().baseUrl();
        Shop.Search.item("Haagen");
        Shop.Cart.collect(5000);
        Shop.Cart.proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.TestVariables.DeliveryPrices.MetroMediumDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при средней корзине" );

        kraken.get().baseUrl();
        Shop.Search.item("Haagen");
        Shop.Cart.collect(10000);
        Shop.Cart.proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.TestVariables.DeliveryPrices.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при большой корзине" );

        kraken.checkout().complete();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.UserProfile.OrderDetailsPage.deliveryPrice()), Config.TestVariables.DeliveryPrices.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки на странице заказа" );

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }
}
