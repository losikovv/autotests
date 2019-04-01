package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;


// Тесты работы с магазином


public class Shopping extends TestBase{

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"regression"},
            priority = 651
    )
    public void noAccessToCheckoutForUnauthorizedUser() throws Exception {
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при пустой корзине",
            groups = {"acceptance","regression"},
            priority = 653
    )
    public void noAccessToCheckoutWithEmptyCart() throws Exception {
        kraken.perform().loginAs(kraken.session.admin);
        kraken.drop().cart();
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест открывания/закрывания карточки продукта",
            groups = {"acceptance","regression"},
            priority = 654
    )
    public void successOperateItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.shopping().openFirstItemCard();

        softAssert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не открывается карточка продукта");

        kraken.shopping().closeItemCard();

        softAssert.assertFalse(kraken.detect().isItemCardOpen(),
                "Не закрывается карточка продукта");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"acceptance","regression"},
            priority = 655
    )
    public void successAddItemToCartFromItemCard()throws Exception, AssertionError {
        kraken.perform().loginAs(kraken.session.user);
        kraken.drop().cart();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();
        kraken.shopping().closeItemCard();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из карточки товара\n");

        kraken.shopping().closeCart();
    }


    @Test(  description = "Тест успешного добавления товара в корзину из сниппета в каталоге",
            groups = {"regression"},
            priority = 656
    )
    public void successAddItemToCartFromCatalog() throws Exception {
        kraken.perform().loginAs(kraken.session.user);
        kraken.drop().cart();

        kraken.shopping().hitFirstItemPlusButton();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из сниппета товара в каталоге\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при сумме корзины меньше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 657
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() throws Exception {
        kraken.perform().loginAs(kraken.session.user);

        if (kraken.detect().isCheckoutButtonActive()) {
            kraken.drop().cart();
        }
        if (kraken.detect().isCartEmpty()) {
            kraken.shopping().closeCart();
            kraken.search().item("хлеб"); // Для случаев когда первый товар на главной дороже минимального заказа
            kraken.shopping().addFirstItemOnPageToCart();
        }

        Assert.assertTrue(!kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive(),
                "Не выполнены предусловия теста");

        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 658
    )
    public void successCollectItemsForMinOrder() throws Exception, AssertionError {
        kraken.perform().loginAs(kraken.session.user);
        kraken.drop().cart();

        kraken.shopping().collectItems();

        Assert.assertTrue(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута не активна, при минимальной сумме заказа в корзине\n");
    }


    @Test(
            description = "Тест доступности чекаута по прямой ссылке при сумме корзины выше минимального заказа",
            groups = {"regression"},
            priority = 659
    )
    public void successAccessCheckoutWithCartAboveMinimalOrderSum() throws Exception {
        kraken.perform().loginAs(kraken.session.user);
        kraken.shopping().collectItems();

        assertPageIsAvailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного перехода из корзины в чекаут при сумме выше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 660
    )
    public void successProceedFromCartToCheckout() throws Exception, AssertionError {
        kraken.perform().loginAs(kraken.session.user);
        kraken.shopping().collectItems();

        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.detect().isOnCheckout(),
                "Не удалось перейти из корзины в чекаут\n");
    }


    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            groups = {"regression"},
            priority = 661
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


    @Test(
            description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером",
            groups = {"regression"},
            priority = 662
    )
    public void successChangeMinOrderSum() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.search().item("молоко");
        kraken.shopping().addFirstItemOnPageToCart();
        int sum1 = kraken.grab().minOrderSum();
        kraken.perform().printMessage("\nСумма минимального первого заказа: " + sum1 + "\n");

        softAssert.assertNotEquals(sum1, 0, "Не отображается сумма минимального первого заказа");

        kraken.perform().order();
        kraken.search().item("молоко");
        kraken.shopping().addFirstItemOnPageToCart();
        int sum2 = kraken.grab().minOrderSum();
        kraken.perform().printMessage("\nСумма минимального повторного заказа: " + sum2 + "\n");

        softAssert.assertNotEquals(sum2, 0, "Не отображается сумма минимального повторного заказа");

        softAssert.assertTrue(sum1 < sum2, "Сумма минимального заказа не изменилась после первого заказа");

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }

}
