package ru.instamart.ui.shopping;

import ru.instamart.core.setting.Config;
import ru.instamart.core.testdata.TestVariables;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.core.testdata.lib.Addresses;
import ru.instamart.core.testdata.lib.Pages;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.ui.TestBase;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public class ShoppingTestsForUnauthorizedUser extends TestBase {

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
    }
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",

            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutByDefault() {
        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру c выбранным адресом и пустой корзиной",

            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutForUnauthorizedUserWithShipAddressAndEmptyCart() {
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();

        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",

            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        Shop.Cart.drop();

        // Для случаев когда первый товар на главной дороже минимального заказа
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.testQuery);
        Shop.Catalog.Item.addToCart();

        Assert.assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                    failMessage("Не выполнены предусловия теста"));

        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",

            groups = {"sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void successCollectItemsForOrder() {
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();

        Shop.Cart.collectFirstTime();

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

        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }
}
