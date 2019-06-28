package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Cart extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест валидации дефолтной корзины",
            groups = {"smoke","acceptance","regression"},
            priority = 620
    )
    public void successValidateEmptyShoppingCart() throws AssertionError {
        ShopHelper.Cart.open();

        Assert.assertTrue(
                kraken.detect().isCartOpen(),
                    "Не открывается корзина\n");

        kraken.check().elementPresence(Elements.Cart.closeButton());
        kraken.check().elementPresence(Elements.Cart.checkoutButton());

        Assert.assertTrue(
                kraken.detect().isCartEmpty(),
                    "Корзина не пуста\n");

        Assert.assertFalse(
                kraken.detect().isCheckoutButtonActive(),
                    "Кнопка чекаута активна в пустой козине\n");

        ShopHelper.Cart.close();

        Assert.assertFalse(
                kraken.detect().isCartOpen(),
                    "Не закрывается корзина\n");
    }

    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"acceptance","regression"},
            priority = 622
    )
    public void successAddItemToCartFromItemCard()throws AssertionError {
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToCart();
        ShopHelper.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется товар в корзину из карточки товара\n");

        ShopHelper.Cart.close();
    }

    @Test(
            description = "Тест на изменение кол-ва товаров в корзине",
            groups = {"acceptance","regression"},
            priority = 623
    )
    public void successChangeItemQuantityInCart() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        ShopHelper.Catalog.Item.addToCart();
        ShopHelper.Cart.open();
        int sum1 = kraken.grab().cartTotalRounded();

        ShopHelper.Cart.Item.increaseQuantity();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum1 < sum2,
                    "Не работает увеличение кол-ва товаров в корзине\n");

        ShopHelper.Cart.Item.decreaseQuantity();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum2 > sum3,
                    "Не работает уменьшение кол-ва товаров в корзине\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на изменение кол-ва товаров в корзине через карточку товара",
            groups = {"acceptance","regression"},
            priority = 624
    )
    public void successChangeItemQuantityInCartViaItemCard() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        ShopHelper.Catalog.Item.addToCart();
        ShopHelper.Cart.open();
        int sum1 = kraken.grab().cartTotalRounded();

        ShopHelper.Cart.Item.open();
        ShopHelper.ItemCard.addToCart();
        ShopHelper.ItemCard.close();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum1 < sum2,
                    "Не работает увеличение кол-ва товаров в корзине\n");

        kraken.perform().click(Elements.Cart.item());
        ShopHelper.ItemCard.removeFromCart();
        ShopHelper.ItemCard.close();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum2 > sum3,
                    "Не работает уменьшение кол-ва товаров в корзине\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на удаление товаров из корзины",
            groups = {"acceptance","regression"},
            priority = 625
    )
    public void successRemoveItemsFromCart() {
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
        //kraken.search().item("хлеб");
        ShopHelper.Catalog.Item.addToCart();
        if(kraken.detect().isCartEmpty()){
            throw new AssertionError(
                    "Не выполнены предусловия теста, корзина пуста");
        }

        kraken.drop().cart();

        Assert.assertTrue(
                kraken.detect().isCartEmpty(),
                    "Не работает удаление товаров из корзины\n");
    }

    @Test(  description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге",
            groups = {"acceptance","regression"},
            priority = 626
    )
    public void successAddItemToCartFromCatalogSnippet() {
        kraken.get().page("metro");
        kraken.perform().loginAs(session.user);
        kraken.drop().cart();

        ShopHelper.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется товар в корзину из сниппета товара в каталоге\n");
        /*
        ShopHelper.Cart.close();
        ShopHelper.Catalog.Item.removeFromCart();

        Assert.assertTrue(
                kraken.detect().isCartEmpty(),
                    "Не удаляется товар из корзины из сниппета товара в каталоге\n");
        */
    }

    //TODO successAddItemToCartFromItemCard()
    //TODO successAddItemToCartFroRRWidgetItem()
    //TODO successAddItemToCartFromFavorites()
    //TODO successAddItemToCartFromSearchResults()
    //TODO successAddItemToCartFromSEOCatalog()

    @Test(
            description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером",
            groups = {"regression"},
            priority = 627
    )
    public void successChangeMinOrderSum() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();

        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.search().item("молоко");
        ShopHelper.Catalog.Item.addToCart();
        int sum1 = kraken.grab().minOrderSum();

        softAssert.assertNotEquals(sum1, 0, "Не отображается сумма минимального первого заказа\n");

        kraken.perform().order();
        kraken.search().item("молоко");
        ShopHelper.Catalog.Item.addToCart();
        int sum2 = kraken.grab().minOrderSum();

        softAssert.assertNotEquals(
                sum2, 0,
                    "Не отображается сумма минимального повторного заказа\n");

        softAssert.assertTrue(
                sum1 < sum2,
                    "Сумма минимального заказа не изменилась после первого заказа\n");

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }
}
