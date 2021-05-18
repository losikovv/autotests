package ru.instamart.test.ui.shopping;

import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.shop.Order;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.test.ui.TestBase;

public class ShoppingCartTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к тестовому прогону")
    public void setup() {
        User.Logout.quickly();
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @Test(
            description = "Тест валидации дефолтной корзины",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateDefaultCart() {
        Shop.Cart.open();

        Assert.assertTrue(
                kraken.detect().isCartOpen(),
                    failMessage("Не открывается корзина"));

        baseChecks.checkIsElementPresent(Elements.Cart.drawer());
        baseChecks.checkIsElementPresent(Elements.Cart.closeButton());
        baseChecks.checkIsElementPresent(Elements.Cart.placeholder());
        baseChecks.checkIsElementPresent(Elements.Cart.checkoutButton());

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
            description = "Тест успешного добавления товара в корзину неавторизованным юзером",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartUnauthorized() {

        Shop.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isCartEmpty(),
                    failMessage("Не добавляется товар в корзину неавторизованным юзером"));

        Shop.Cart.close();
    }

    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartFromItemCard() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultUser());
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
            groups = {"sbermarket-regression"}
    )
    public void successChangeItemQuantityInCart() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultUser());
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
            groups = {"sbermarket-regression"}
    )
    public void successChangeItemQuantityInCartViaItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultUser());
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successRemoveItemsFromCart() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultUser());
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartFromCatalogSnippet() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.loginAs(UserManager.getDefaultUser());
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
            groups = {"sbermarket-regression"}
    )
    public void successChangeMinOrderSum() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();

        User.Do.registration();
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        Shop.Search.searchItem("молоко");
        Shop.Catalog.Item.addToCart();
        int sum1 = kraken.grab().minOrderSum();

        softAssert.assertNotEquals(sum1, 0, "Не отображается сумма минимального первого заказа\n");

        Order.order();
        Shop.Search.searchItem("молоко");
        Shop.Catalog.Item.addToCart();
        int sum2 = kraken.grab().minOrderSum();

        softAssert.assertNotEquals(
                sum2, 0,
                    "Не отображается сумма минимального повторного заказа\n");

        softAssert.assertTrue(
                sum1 < sum2,
                    "Сумма минимального заказа не изменилась после первого заказа\n");

        Order.cancelLastActiveOrder();
        softAssert.assertAll();
    }
}
