package ru.instamart.tests.shopping;

import instamart.core.settings.Config;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class ShoppingTestsForUnauthorizedUser extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            priority = 651,
            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutByDefault() {
        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру c выбранным адресом и пустой корзиной",
            priority = 652,
            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutForUnauthorizedUserWithShipAddressAndEmptyCart() {
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            priority = 653,
            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
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
            priority = 654,
            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void successCollectItemsForOrder() {
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();

        Assert.assertTrue(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута не активна при минимальной сумме заказа в корзине"));

        Shop.Cart.proceedToCheckout();

        Assert.assertFalse(
                kraken.detect().isOnCheckout(),
                    failMessage("Можно перейти в чекаут неаторизованным юзером с набранной корзиной"));

        Assert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    failMessage("Не запрашивается авторизация при попытке перехода в чекаут неавторизованным юзером с набранной корзиной"));

        assertPageIsUnavailable(Pages.checkout());
    }
}
