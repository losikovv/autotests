package ru.instamart.autotests.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class BasicShoppingTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        ShopHelper.ShippingAddress.set(Addresses.Moscow.defaultAddress());
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
        kraken.perform().loginAs(session.admin);
        kraken.get().page("metro");
        ShopHelper.Cart.drop();

        assertPageIsUnavailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при сумме корзины меньше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 653
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");

        if (kraken.detect().isCheckoutButtonActive()) {
            ShopHelper.Cart.drop();
        }

        if (kraken.detect().isCartEmpty()) {
            ShopHelper.Cart.close();
            ShopHelper.Search.item("хлеб"); // Костыль для случаев когда первый товар на главной дороже минимального заказа
            ShopHelper.Catalog.Item.addToCart();
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
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        ShopHelper.Cart.drop();

        ShopHelper.Cart.collect();

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
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        ShopHelper.Cart.collect();

        assertPageIsAvailable(
                Pages.Site.checkout());
    }

    @Test(
            description = "Тест успешного перехода из корзины в чекаут при сумме выше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 656
    )
    public void successProceedFromCartToCheckout() {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        ShopHelper.Cart.collect();

        ShopHelper.Cart.proceedToCheckout();

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
        kraken.perform().registration(testuser);
        ShopHelper.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        ShopHelper.Catalog.Item.addToCart();
        kraken.perform().quickLogout();

        kraken.get().page("metro");
        ShopHelper.ShippingAddress.set(Addresses.Moscow.testAddress());
        kraken.perform().authorisation(testuser);

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
