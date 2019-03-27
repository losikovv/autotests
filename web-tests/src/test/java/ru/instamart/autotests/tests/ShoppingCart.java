package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;

public class ShoppingCart extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
    }


    @Test(
            description = "Тест работы с пустой корзиной",
            groups = {"acceptance","regression"},
            priority = 621
    )
    public void successOperateEmptyCart() throws Exception, AssertionError {
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

    // TODO тест на появляение и пропадение в корзине товара добавленного/убранного из каталога

    @Test(
            description = "Тест на изменение кол-ва товаров в корзине",
            groups = {"regression"},
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
                "Не работает увеличение кол-ва товаров в корзине");

        kraken.shopping().decreaseItemNumberInCart();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(sum2 > sum3,
                "Не работает уменьшение кол-ва товаров в корзине");

        softAssert.assertAll();
    }

    // TODO тест на увеличение и уменьшение товара в корзине переходом в карточку из корзины

    @Test(
            description = "Тест на удаление товаров из корзины",
            groups = {"regression"},
            priority = 625
    )
    public void successRemoveItemsFromCart() throws Exception {
        kraken.search().item("хлеб");
        kraken.shopping().addFirstItemOnPageToCart();

        kraken.drop().cart();

        Assert.assertTrue(kraken.detect().isCartEmpty(),
                "Не работает удаление товаров из корзины");
    }
}
