package ru.instamart.tests.shopping;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

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
            priority = 661,
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
            priority = 662,
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
            Shop.Search.existingItem();
            Shop.Catalog.Item.addToCart();
        }

        kraken.get().page(Config.DEFAULT_RETAILER);
        Assert.assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                failMessage("Не выполнены предусловия теста"));
        baseChecks.checkPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для оформления заказа",
            priority = 663,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression"
            }
    ) public void successCollectItemsForMinOrder() {
        SoftAssert softAssert = new SoftAssert();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

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
            priority = 664,
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
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        kraken.perform().refresh();
        Shop.Catalog.Item.addToCart();
        User.Logout.quickly();

        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
        User.Auth.withEmail(testuser);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                failMessage("Не удалось авторизоваться"));

        softAssert.assertTrue(
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
