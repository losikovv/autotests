package ru.instamart.tests.ui.shopping;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.TestVariables;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.common.pagesdata.UserData;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.modules.shop.ShippingAddressModal;

import static org.testng.Assert.assertTrue;

public class ShoppingTestsForExistingUser extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к тестовому прогону")
    public void setup() {
        User.Logout.quickly();
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.apiV2().dropCart(UserManager.getDefaultUser(), RestAddresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutForAuthorizedUserWithShipAddressAndEmptyCart() {
        kraken.apiV2().dropCart(UserManager.getDefaultUser(), RestAddresses.Moscow.defaultAddress());
        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {

        if (kraken.detect().isCheckoutButtonActive()) {
            kraken.apiV2().dropCart(UserManager.getDefaultUser(), RestAddresses.Moscow.defaultAddress());
        }

        if (kraken.detect().isCartEmpty()) {
            Shop.Cart.close();
            Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.testQuery);
            Shop.Catalog.Item.addToCart();
        }

        kraken.get().page(Config.DEFAULT_RETAILER);
        assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                failMessage("Не выполнены предусловия теста"));
        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для оформления заказа",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void successCollectItemsForMinOrder() {
        SoftAssert softAssert = new SoftAssert();
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();

        Shop.Cart.collect();

        softAssert.assertTrue(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута не активна при минимальной сумме заказа в корзине"));

        Shop.Cart.proceedToCheckout();

        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                    failMessage("Не удалось перейти из корзины в чекаут"));

        baseChecks.checkPageIsAvailable(Pages.checkout());
        softAssert.assertAll();
    }

    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void successMergeShipAddressAndCartAfterAuthorisation() {
        SoftAssert softAssert = new SoftAssert();

        //TODO переписать под набор корзины и авторизацию + проверку адреса и переход в чекаут
        final UserData testuser = UserManager.getUser();
        User.Logout.quickly();
        kraken.get().baseUrl();
        User.Do.registration(testuser);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        kraken.perform().refresh();
        Shop.Catalog.Item.addToCart();
        User.Logout.quickly();

        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        User.Auth.withEmail(testuser);

        assertTrue(
                kraken.detect().isUserAuthorised(),
                failMessage("Не удалось авторизоваться"));

        assertTrue(
                kraken.detect().isShippingAddressSet(),
                failMessage("Слетел адрес доставки при авторизации"));

        softAssert.assertEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                failMessage("Не обновился адрес доставки при авторизации\n"
                + "Ожидаемый адрес: " + Addresses.Moscow.defaultAddress() + "\n"
                + "Текущий адрес: " + kraken.grab().currentShipAddress()));

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                failMessage("Не смержиласть корзина при авторизации"));

        softAssert.assertAll();
    }
}
