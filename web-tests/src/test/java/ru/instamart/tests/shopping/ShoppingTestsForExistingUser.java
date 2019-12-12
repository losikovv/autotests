package ru.instamart.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.rest.RestAddresses;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

public class ShoppingTestsForExistingUser extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        User.Do.loginAs(AppManager.session.user);
        kraken.rest().dropCart(AppManager.session.user, RestAddresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест недоступности чекаута по прямой ссылке авторизованному юзеру c выбранным адресом и пустой корзиной",
            priority = 661,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression",
            }
    ) public void noAccessToCheckoutForAuthorizedUserWithShipAddressAndEmptyCart() {
        kraken.rest().dropCart(AppManager.session.user, RestAddresses.Moscow.defaultAddress());

        assertPageIsUnavailable(Pages.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута при сумме корзины меньше минимального заказа",
            priority = 662,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression",
            }
    ) public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {

        if (kraken.detect().isCheckoutButtonActive()) {
            kraken.rest().dropCart(AppManager.session.user, RestAddresses.Moscow.defaultAddress());
        }

        if (kraken.detect().isCartEmpty()) {
            Shop.Cart.close();
            Shop.Search.item("хлеб"); // Костыль для случаев когда первый товар на главной дороже минимального заказа
            Shop.Catalog.Item.addToCart();
        }

        kraken.get().page("metro");
        Assert.assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                failMessage("Не выполнены предусловия теста"));

        assertPageIsUnavailable(
                Pages.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для оформления заказа",
            priority = 663,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression",
            }
    ) public void successCollectItemsForMinOrder() {
        SoftAssert softAssert = new SoftAssert();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();

        softAssert.assertTrue(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута не активна при минимальной сумме заказа в корзине"));

        Shop.Cart.proceedToCheckout();

        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                    failMessage("Не удалось перейти из корзины в чекаут"));

        assertPageIsAvailable(Pages.checkout());
        softAssert.assertAll();
    }

    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            priority = 664,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression",
                    "metro-acceptance","metro-regression",
            }
    ) public void successMergeShipAddressAndCartAfterAuthorisation() {
        SoftAssert softAssert = new SoftAssert();

        //TODO переписать под набор корзины и авторизацию + проверку адреса и переход в чекаут
        final UserData testuser = generate.testCredentials("user");
        User.Logout.quickly();
        kraken.get().baseUrl();
        User.Do.registration(testuser);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        kraken.await().simply(5); //todo протестить
        Shop.Catalog.Item.addToCart();
        User.Logout.quickly();

        kraken.get().page("metro");
        User.ShippingAddress.set(Addresses.Moscow.testAddress());
        User.Auth.withEmail(testuser);

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                failMessage("Не удалось авторизоваться"));

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                failMessage("Слетел адрес доставки при авторизации"));

        softAssert.assertEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                failMessage("Не обновился адрес доставки при авторизации"));

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                failMessage("Не смержиласть корзина при авторизации"));

        softAssert.assertAll();
    }
}
