package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;


// Тесты магазина


public class Shopping extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        kraken.get().page("metro");
    }


    @Test(
            description = "Тест недоступности чекаута неавторизованному юзеру",
            groups = {"regression"},
            priority = 351
    )
    public void noAccessToCheckoutForUnauthorizedUser() throws Exception {
        kraken.perform().quickLogout();
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест работы с пустой корзиной",
            groups = {"acceptance","regression"},
            priority = 352
    )
    public void successOperateEmptyCart() throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();
        kraken.shopping().openCart();

        Assert.assertTrue(kraken.detect().isCartOpen(),
                "Не открывается корзина\n");

        Assert.assertTrue(kraken.detect().isCartEmpty(), // TODO вынести в отдельный тест на дроп корзины
                "Корзина не пуста\n");

        Assert.assertFalse(kraken.detect().isCheckoutButtonActive(), // TODO вынести в отдельный тест на дроп корзины
                "Кнопка чекаута активна в пустой козине\n");

        kraken.shopping().closeCart();

        Assert.assertFalse(kraken.detect().isCartOpen(),
                "Не закрывается корзина\n");
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при пустой корзине",
            groups = {"acceptance","regression"},
            priority = 353
    )
    public void noAccessToCheckoutWithEmptyCart() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    // TODO добавить тест successOperateItemCard
    // открываем первую карточку товара на страницу
    // проверяем что открыта
    // закрывваем
    // проверяем что закрыта


    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"acceptance","regression"},
            priority = 354
    )
    public void successAddItemToCartFromItemCard()throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();

        kraken.shopping().addFirstItemOnPageToCart();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из карточки товара\n");

        kraken.shopping().closeCart();
    }


    @Test( enabled = false, // TODO починить тест
            description = "Тест успешного добавления товара в корзину из сниппета в каталоге",
            groups = {"regression"},
            priority = 355
    )
    public void successAddItemToCartFromCatalog() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.perform().dropCart();

        kraken.shopping().hitFirstItemPlusButton(); // TODO починить добавление в корзину товара из сниппета

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из сниппета товара в каталоге\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест недоступности чекаута по прямой ссылке при сумме корзины меньше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 356
    )
    public void noAccessToCheckoutWithCartBelowMinimalOrderSum() throws Exception {
        kraken.perform().loginAs("admin");

        if (kraken.detect().isCheckoutButtonActive()) {
            kraken.perform().dropCart();
        }
        if(kraken.detect().isCartEmpty()) {
            kraken.shopping().closeCart();
            kraken.search().item("хлеб"); // Для случаев когда первый товар на главной дороже минимального заказа
            kraken.shopping().addFirstItemOnPageToCart();
        }

        Assert.assertTrue(!kraken.detect().isCartEmpty() && !kraken.detect().isCheckoutButtonActive());
        assertPageIsUnavailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест набора корзины до суммы, достаточной для заказа",
            groups = {"acceptance","regression"},
            priority = 357
    )
    public void successCollectItemsForMinOrder() throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.shopping().collectItems();

        Assert.assertTrue(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута не активна, при минимальной сумме заказа в корзине\n");
    }


    @Test(
            description = "Тест доступности чекаута по прямой ссылке при сумме корзины выше минимального заказа",
            groups = {"regression"},
            priority = 358
    )
    public void successAccessCheckoutWithCartAboveMinimalOrderSum() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.shopping().collectItems();

        assertPageIsAvailable(Pages.Site.checkout());
    }


    @Test(
            description = "Тест успешного перехода из корзины в чекаут при сумме выше минимального заказа",
            groups = {"acceptance","regression"},
            priority = 359
    )
    public void successProceedFromCartToCheckout()throws Exception, AssertionError {
        kraken.perform().loginAs("admin");
        kraken.shopping().collectItems();

        kraken.shopping().proceedToCheckout();

        Assert.assertTrue(kraken.checkout().isOnCheckout(),
                "Не удалось перейти из корзины в чекаут\n");
    }


    @Test(
            description = "Тест на подтягивание адреса и мердж корзины из профиля при авторизации",
            groups = {"regression"},
            priority = 360
    )
    public void successMergeShipAddressAndCartAfterAuthorisation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        //TODO вынести в dataProvider
        kraken.perform().dropAuth();
        final UserData testuser = Generate.testUserData();
        kraken.get().baseUrl();
        kraken.perform().registration(testuser);
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().addFirstItemOnPageToCart();
        kraken.perform().logout();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.perform().login(testuser);

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

    // TODO тест на изменение суммы минимального заказа после первого заказ новым юзером (+ ДОБАВИТЬ МЕТОД УЗНАЮЩИЙ ТЕКУЩУЮ СУММУ МИН ЗАКАЗА ИЗ ПОДСКАЗКИ В КОРЗИНЕ)

    // TODO тест на изменение кол-ва товаров в корзине

    // TODO тест на удаление товаров из корзины

}
