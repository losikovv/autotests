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
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

public class BasicShoppingTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"acceptance","regression"},
            priority = 651
    )
    public void noAccessToCheckoutForUnauthorizedUser() {
        assertPageIsUnavailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при пустой корзине",
            groups = {"acceptance","regression"},
            priority = 652
    )
    public void noAccessToCheckoutWithEmptyCart() {
        User.Do.loginAs(AppManager.session.admin);
        kraken.get().page("metro");
        Shop.Cart.drop();

        assertPageIsUnavailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при сумме корзины меньше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 653
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        User.Do.loginAs(AppManager.session.user);
        kraken.get().page("metro");

        if (kraken.detect().isCheckoutButtonActive()) {
            Shop.Cart.drop();
        }

        if (kraken.detect().isCartEmpty()) {
            Shop.Cart.close();
            Shop.Search.item("хлеб"); // Костыль для случаев когда первый товар на главной дороже минимального заказа
            Shop.Catalog.Item.addToCart();
        }

        Assert.assertTrue(
                !kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                    failMessage("Не выполнены предусловия теста"));

        assertPageIsUnavailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 654
    )
    public void successCollectItemsForMinOrder() {
        User.Do.loginAs(AppManager.session.user);
        Shop.Cart.drop();
        kraken.get().page("metro");

        Shop.Cart.collect();

        Assert.assertTrue(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута не активна, при минимальной сумме заказа в корзине"));
    }

    @Test(
            description = "Тест доступности чекаута по прямой ссылке при сумме корзины выше минимального заказа",
            groups = {"regression"},
            priority = 655
    )
    public void successGetCheckoutPageWithCartAboveMinimalOrderSum() {
        User.Do.loginAs(AppManager.session.user);
        kraken.get().page("metro");

        Shop.Cart.collect();

        assertPageIsAvailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест успешного перехода из корзины в чекаут при сумме выше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 656
    )
    public void successProceedFromCartToCheckout() {
        User.Do.loginAs(AppManager.session.user);
        kraken.get().page("metro");
        Shop.Cart.collect();

        Shop.Cart.proceedToCheckout();

        Assert.assertTrue(
                kraken.detect().isOnCheckout(),
                    failMessage("Не удалось перейти из корзины в чекаут"));
    }

    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            groups = {"regression"},
            priority = 657
    )
    public void successMergeShipAddressAndCartAfterAuthorisation() {
        SoftAssert softAssert = new SoftAssert();

        //TODO вынести в dataProvider
        final UserData testuser = generate.testCredentials("user");
        kraken.get().baseUrl();
        User.Do.registration(testuser);
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Catalog.Item.addToCart();
        User.Logout.quickly();

        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.testAddress());
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
