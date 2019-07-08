package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class OrderDeliveryPrices extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
    }

    // TODO переделать тесты
    @Test(
            description = "Тест проверки прогрессивной стоимости доставки в Метро",
            groups = {"regression"},
            priority = 801
    )
    public void successCheckMetroDeliveryPriceDiscount() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.drop().cart();

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().fillOrderDetails();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.MetroHighDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при малой корзине");

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems(5000);
        ShopHelper.Cart.proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.MetroMediumDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при средней корзине" );

        kraken.get().baseUrl();
        kraken.search().item("Haagen");
        kraken.shopping().collectItems(10000);
        ShopHelper.Cart.proceedToCheckout();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.Checkout.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки в чекауте при большой корзине" );

        kraken.checkout().complete();
        softAssert.assertEquals(kraken.grab().roundedSum(Elements.UserProfile.OrderDetailsPage.deliveryPrice()), Config.MetroLowDeliveryPrice,
                "\nНекорректная цена доставки на странице заказа" );

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }
}
