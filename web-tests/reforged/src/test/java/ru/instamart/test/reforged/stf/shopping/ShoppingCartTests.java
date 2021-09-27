package ru.instamart.test.reforged.stf.shopping;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.config.CoreProperties.DEFAULT_SMS;
import static ru.instamart.reforged.stf.page.StfRouter.*;

public class ShoppingCartTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @Test(
            description = "Тест валидации дефолтной корзины",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateDefaultCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartPlaceholderIsVisible();
        shop().interactCart().checkCartCloseButtonIsVisible();
        shop().interactCart().checkCartReturnToCatalogButtonIsVisible();

        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();
    }

    @Test(
            description = "Тест успешного добавления товара в корзину неавторизованным юзером",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartUnauthorized() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @Test(
            description = "Тест успешного добавления товара в корзину из карточки товара",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartFromItemCard() {
        final UserData shoppingCartUser = UserManager.getUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(shoppingCartUser.getPhone());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        //TODO: Костыль из-за бейсик авторизации
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + shop().pageUrl());

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @Test(
            description = "Тест на изменение кол-ва товаров в корзине",
            groups = {"sbermarket-regression"}
    )
    public void successChangeItemQuantityInCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().plusFirstItemToCart();
        shop().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        final double startAmount = shop().interactCart().returnOrderAmount();

        shop().interactCart().increaseCount();
        shop().interactCart().checkSpinnerIsNotVisible();
        final double increasedAmount = shop().interactCart().returnOrderAmount();

        shop().interactCart().decreaseCount();
        shop().interactCart().checkSpinnerIsNotVisible();
        final double decreasedAmount = shop().interactCart().returnOrderAmount();

        Assert.assertTrue(startAmount < increasedAmount,
                "Не работает увеличение кол-ва товаров в корзине");
        Assert.assertTrue(decreasedAmount < increasedAmount,
                "Не работает уменьшение кол-ва товаров в корзине");
    }

    // TODO починить тест
    @Test(
            description = "Тест на изменение кол-ва товаров в корзине через карточку товара",
            groups = {"sbermarket-regression"}
    )
    public void successChangeItemQuantityInCartViaItemCard() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + shop().pageUrl());

        shop().checkSpinnerIsNotVisible();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().goToPage();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().clickToCart();
        final double startAmount = shop().interactCart().returnOrderAmount();

        shop().goToPage();
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + shop().pageUrl());

        shop().checkSpinnerIsNotVisible();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToCart();
        final double increasedAmount = shop().interactCart().returnOrderAmount();

        shop().goToPage();
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + shop().pageUrl());

        shop().checkSpinnerIsNotVisible();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
        shop().interactHeader().clickToCart();
        final double decreasedAmount = shop().interactCart().returnOrderAmount();

        shop().goToPage();
        Assert.assertTrue(startAmount < increasedAmount,
                "Не работает увеличение кол-ва товаров в корзине");
        Assert.assertTrue(decreasedAmount < increasedAmount,
                "Не работает уменьшение кол-ва товаров в корзине");
    }

    @Test(
            description = "Тест на удаление товаров из корзины",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successRemoveItemsFromCart() {
        final UserData shoppingCartUser = UserManager.getUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().fillPhone(shoppingCartUser.getPhone());
        shop().interactAuthModal().sendSms();
        shop().interactAuthModal().fillSMS(DEFAULT_SMS);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCart();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().
    }

    @Test(  description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartFromCatalogSnippet() {
        kraken.get().page(CoreProperties.DEFAULT_RETAILER);
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
