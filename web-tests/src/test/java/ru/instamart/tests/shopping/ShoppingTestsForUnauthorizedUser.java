package ru.instamart.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class ShoppingTestsForUnauthorizedUser extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("metro");
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 651
    )
    public void noAccessToCheckoutByDefault() {
        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру c выбранным адресом и пустой корзиной",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 652
    )
    public void noAccessToCheckoutForUnauthorizedUserWithShipAddressAndEmptyCart() {
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 653
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Cart.drop();

        // Для случаев когда первый товар на главной дороже минимального заказа
        Shop.Search.item("хлеб");
        Shop.Catalog.Item.addToCart();

        Assert.assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                    failMessage("Не выполнены предусловия теста"));

        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 654
    )
    public void successCollectItemsForOrder() {
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();

        Assert.assertTrue(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута не активна при минимальной сумме заказа в корзине"));
    }
}
