package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class ShoppingCart extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.drop().cart();
    }


    @Test(
            description = "Тест работы с корзиной",
            groups = {"acceptance","regression"},
            priority = 620
    )
    public void successOperateShoppingCart() throws AssertionError {
        kraken.shopping().openCart();

        Assert.assertTrue(kraken.detect().isCartOpen(),
                "Не открывается корзина\n");

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Корзина не пуста\n");

        Assert.assertFalse(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута активна в пустой козине\n");

        kraken.shopping().closeCart();

        Assert.assertFalse(kraken.detect().isCartOpen(),
                "Не закрывается корзина\n");
    }


    @Test(
            description = "Тест пустой корзины по дефолту",
            groups = {"acceptance","regression"},
            priority = 621
    )
    public void emptyShoppingCartByDefault() throws AssertionError {
        kraken.shopping().openCart();

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Корзина не пуста по дефолту\n");

        Assert.assertFalse(kraken.detect().isCheckoutButtonActive(),
                "Кнопка чекаута активна в пустой козине\n");
    }

    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"acceptance","regression"},
            priority = 622
    )
    public void successAddItemToCartFromItemCard()throws Exception, AssertionError {
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();
        kraken.shopping().closeItemCard();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из карточки товара\n");

        kraken.shopping().closeCart();
    }


    @Test(
            description = "Тест на изменение кол-ва товаров в корзине",
            groups = {"acceptance","regression"},
            priority = 623
    )
    public void successChangeItemQuantityInCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.shopping().addFirstItemOnPageToCart();
        kraken.shopping().openCart();
        int sum1 = kraken.grab().cartTotalRounded();

        kraken.shopping().increaseItemNumberInCart();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(sum1 < sum2,
                "Не работает увеличение кол-ва товаров в корзине\n");

        kraken.shopping().decreaseItemNumberInCart();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(sum2 > sum3,
                "Не работает уменьшение кол-ва товаров в корзине\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на изменение кол-ва товаров в корзине через карточку товара",
            groups = {"acceptance","regression"},
            priority = 624
    )
    public void successChangeItemQuantityInCartViaItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.shopping().addFirstItemOnPageToCart();
        kraken.shopping().openCart();
        int sum1 = kraken.grab().cartTotalRounded();

        kraken.perform().click(Elements.Site.Cart.item());
        kraken.shopping().hitPlusButton();
        kraken.shopping().closeItemCard();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(sum1 < sum2,
                "Не работает увеличение кол-ва товаров в корзине\n");

        kraken.perform().click(Elements.Site.Cart.item());
        kraken.shopping().hitMinusButton();
        kraken.shopping().closeItemCard();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(sum2 > sum3,
                "Не работает уменьшение кол-ва товаров в корзине\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на удаление товаров из корзины",
            groups = {"acceptance","regression"},
            priority = 625
    )
    public void successRemoveItemsFromCart() throws Exception {
        kraken.search().item("хлеб");
        kraken.shopping().addFirstItemOnPageToCart();

        kraken.drop().cart();

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Не работает удаление товаров из корзины\n");
    }


    @Test(  description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге",
            groups = {"acceptance","regression"},
            priority = 626
    )
    public void successAddItemToCartFromCatalog() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        kraken.shopping().hitFirstItemPlusButton();

        Assert.assertFalse(kraken.detect().isCartEmpty(),
                "Не добавляется товар в корзину из сниппета товара в каталоге\n");

        kraken.shopping().closeCart();
        kraken.shopping().hitFirstItemMinusButton();

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Не удаляется товар из корзины из сниппета товара в каталоге\n");

    }


    @Test(
            description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером",
            groups = {"regression"},
            priority = 627
    )
    public void successChangeMinOrderSum() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.search().item("молоко");
        kraken.shopping().addFirstItemOnPageToCart();
        int sum1 = kraken.grab().minOrderSum();
        kraken.perform().printMessage("\nСумма минимального первого заказа: " + sum1 + "\n");

        softAssert.assertNotEquals(sum1, 0, "Не отображается сумма минимального первого заказа\n");

        kraken.perform().order();
        kraken.search().item("молоко");
        kraken.shopping().addFirstItemOnPageToCart();
        int sum2 = kraken.grab().minOrderSum();
        kraken.perform().printMessage("\nСумма минимального повторного заказа: " + sum2 + "\n");

        softAssert.assertNotEquals(sum2, 0, "Не отображается сумма минимального повторного заказа\n");

        softAssert.assertTrue(sum1 < sum2, "Сумма минимального заказа не изменилась после первого заказа\n");

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }
}
