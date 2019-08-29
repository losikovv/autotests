package ru.instamart.tests.orders;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

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
