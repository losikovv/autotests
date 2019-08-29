package ru.instamart.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

public class ShoppingCartTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test(
            description = "Тест валидации дефолтной корзины",
            groups = {"smoke","acceptance","regression"},
            priority = 620
    )
    public void successValidateEmptyShoppingCart() {
        Shop.Cart.open();

        Assert.assertTrue(
                kraken.detect().isCartOpen(),
                    failMessage("Не открывается корзина"));

        // todo добавить больше элементов
        assertElementPresence(Elements.Cart.closeButton());
        assertElementPresence(Elements.Cart.checkoutButton());

        Assert.assertTrue(
                kraken.detect().isCartEmpty(),
                    failMessage("Корзина не пуста по дефолту для нового неавторизованного юзера"));

        Assert.assertFalse(
                kraken.detect().isCheckoutButtonActive(),
                    failMessage("Кнопка чекаута активна при пустой козине"));

        Shop.Cart.close();

        Assert.assertFalse(
                kraken.detect().isCartOpen(),
                    failMessage("Не закрывается корзина"));
    }

    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"acceptance","regression"},
            priority = 622
    )
    public void successAddItemToCartFromItemCard() {
        kraken.get().page("metro");
        User.Do.loginAs(session.user);
        Shop.Cart.drop();

        Shop.Catalog.Item.open();
            Shop.ItemCard.addToCart();
        Shop.ItemCard.close();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    failMessage("Не добавляется товар в корзину из карточки товара"));

        Shop.Cart.close();
    }

    // TODO починить изменение кол-ва товаров в корзине
    @Test(
            description = "Тест на изменение кол-ва товаров в корзине",
            groups = {"regression"},
            priority = 623
    )
    public void successChangeItemQuantityInCart() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        User.Do.loginAs(session.user);
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart();
        Shop.Cart.open();
        int sum1 = kraken.grab().cartTotalRounded();

        Shop.Cart.Item.increaseQuantity();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum1 < sum2,
                    failMessage("Не работает увеличение кол-ва товаров в корзине"));

        Shop.Cart.Item.decreaseQuantity();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum2 > sum3,
                    failMessage("Не работает уменьшение кол-ва товаров в корзине"));

        softAssert.assertAll();
    }

    // TODO починить тест
    @Test(
            description = "Тест на изменение кол-ва товаров в корзине через карточку товара",
            groups = {"regression"},
            priority = 624
    )
    public void successChangeItemQuantityInCartViaItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        User.Do.loginAs(session.user);
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart();
        Shop.Cart.open();
        int sum1 = kraken.grab().cartTotalRounded();

        Shop.Cart.Item.open();
        Shop.ItemCard.addToCart();
        Shop.ItemCard.close();
        int sum2 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum1 < sum2,
                    failMessage("Не работает увеличение кол-ва товаров в корзине"));

        Shop.Cart.Item.open();
        kraken.perform().click(Elements.Cart.item.openButton());
        Shop.ItemCard.removeFromCart();
        Shop.ItemCard.close();
        int sum3 = kraken.grab().cartTotalRounded();

        softAssert.assertTrue(
                sum2 > sum3,
                    failMessage("Не работает уменьшение кол-ва товаров в корзине"));

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на удаление товаров из корзины",
            groups = {"acceptance","regression"},
            priority = 625
    )
    public void successRemoveItemsFromCart() {
        kraken.get().page("metro");
        User.Do.loginAs(session.user);
        //kraken.search().item("хлеб");
        Shop.Catalog.Item.addToCart();
        if(kraken.detect().isCartEmpty()){
            throw new AssertionError(
                    "Не выполнены предусловия теста, корзина пуста");
        }

        Shop.Cart.drop();

        Assert.assertTrue(
                kraken.detect().isCartEmpty(),
                    failMessage("Не работает удаление товаров из корзины"));
    }

    @Test(  description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге",
            groups = {"acceptance","regression"},
            priority = 626
    )
    public void successAddItemToCartFromCatalogSnippet() {
        kraken.get().page("metro");
        User.Do.loginAs(session.user);
        Shop.Cart.drop();

        Shop.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "Не добавляется товар в корзину из сниппета товара в каталоге\n");
        /*
        Shop.Cart.close();
        Shop.Catalog.Item.removeFromCart();

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
        User.Do.quickLogout();

        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Search.item("молоко");
        Shop.Catalog.Item.addToCart();
        int sum1 = kraken.grab().minOrderSum();

        softAssert.assertNotEquals(sum1, 0, "Не отображается сумма минимального первого заказа\n");

        kraken.perform().order();
        Shop.Search.item("молоко");
        Shop.Catalog.Item.addToCart();
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
