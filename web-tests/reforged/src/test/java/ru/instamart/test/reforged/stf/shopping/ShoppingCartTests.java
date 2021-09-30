package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
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

@Epic("STF UI")
@Feature("Основные тесты корзины")
public class ShoppingCartTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1571)
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

    @CaseId(1572)
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

    @CaseId(1573)
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

    @CaseId(1574)
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
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareFirstItemQuantityInCart(1);

        shop().interactCart().increaseCount();
        shop().interactCart().checkSpinnerIsVisible();
        shop().interactCart().checkSpinnerIsNotVisible();
        shop().interactCart().compareFirstItemQuantityInCart(2);

        shop().interactCart().decreaseCount();
        shop().interactCart().checkSpinnerIsVisible();
        shop().interactCart().checkSpinnerIsNotVisible();
        shop().interactCart().compareFirstItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1575)
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
        shop().interactCart().compareFirstItemQuantityInCart(1);

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
        shop().interactCart().compareFirstItemQuantityInCart(2);

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
        shop().interactCart().compareFirstItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1576)
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
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().deleteFirstItem();
        shop().interactCart().checkSpinnerIsNotVisible();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1577)
    @Test(  description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddItemToCartFromCatalogSnippet() {
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
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().deleteFirstItem();
        shop().interactCart().checkSpinnerIsNotVisible();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1578)
    @Test(
            description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером",
            groups = {"sbermarket-regression"}
    )
    public void successChangeMinOrderSum() {
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
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        final double firstOrderMinAmount = shop().interactCart().returnMinOrderAmount();

        helper.makeOrder(shoppingCartUser, EnvironmentProperties.DEFAULT_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();
        search().checkAddToCartButtonVisible();
        search().clickAddToCartFirstSearchResult();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        final double repeatedOrderMinAmount = shop().interactCart().returnMinOrderAmount();

        shop().interactCart().checkFirstMinAmountMoreThanRepeated(firstOrderMinAmount, repeatedOrderMinAmount);
        shop().assertAll();
    }
}
