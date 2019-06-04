package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Shopping extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }


    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"acceptance","regression"},
            priority = 651
    )
    public void noAccessToCheckoutForUnauthorizedUser() throws Exception {
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при пустой корзине",
            groups = {"acceptance","regression"},
            priority = 652
    )
    public void noAccessToCheckoutWithEmptyCart() throws Exception {
        kraken.perform().loginAs(session.admin);
        kraken.get().page("metro");
        kraken.drop().cart();
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при сумме корзины меньше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 653
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");

        if (kraken.detect().isCheckoutButtonActive()) {
            kraken.drop().cart();
        }
        if (kraken.detect().isCartEmpty()) {
            ShopHelper.Cart.close();
            kraken.search().item("хлеб"); // Костыль для случаев когда первый товар на главной дороже минимального заказа
            kraken.shopping().addFirstItemOnPageToCart();
        }

        Assert.assertTrue(!kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                "Не выполнены предусловия теста\n");

        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 654
    )
    public void successCollectItemsForMinOrder() throws Exception, AssertionError {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        kraken.drop().cart();

        kraken.shopping().collectItems();

        Assert.assertTrue(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута не активна, при минимальной сумме заказа в корзине\n");
    }


    @Test(
            description = "Тест доступности чекаута по прямой ссылке при сумме корзины выше минимального заказа",
            groups = {"regression"},
            priority = 655
    )
    public void successGetCheckoutPageWithCartAboveMinimalOrderSum() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        kraken.shopping().collectItems();

        assertPageIsAvailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного перехода из корзины в чекаут при сумме выше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 656
    )
    public void successProceedFromCartToCheckout() throws Exception, AssertionError {
        kraken.perform().loginAs(session.user);
        kraken.get().page("metro");
        kraken.shopping().collectItems();

        ShopHelper.Cart.proceedToCheckout();

        Assert.assertTrue(kraken.detect().isOnCheckout(),
                "Не удалось перейти из корзины в чекаут\n");
    }


    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            groups = {"regression"},
            priority = 657
    )
    public void successMergeShipAddressAndCartAfterAuthorisation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        //TODO вынести в dataProvider
        final UserData testuser = generate.testCredentials("user");
        kraken.get().baseUrl();
        kraken.perform().registration(testuser);
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().addFirstItemOnPageToCart();
        kraken.perform().quickLogout();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.perform().authorisation(testuser);

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не удалось авторизоваться\n");

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Слетел адрес доставки при авторизации\n");

        softAssert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                "Не обновился адрес доставки при авторизации\n");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "Не смержиласть корзина при авторизации\n");

        softAssert.assertAll();
    }
}
