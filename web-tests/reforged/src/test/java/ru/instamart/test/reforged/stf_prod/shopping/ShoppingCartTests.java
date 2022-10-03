package ru.instamart.test.reforged.stf_prod.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1571)
    @Test(description = "Тест валидации дефолтной корзины", groups = {STF_PROD_S})
    public void successValidateDefaultCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.trainingAddressProd());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
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
    @Test(description = "Тест успешного добавления товара в корзину неавторизованным юзером", groups = {STF_PROD_S})
    public void successAddItemToCartUnauthorized() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.trainingAddressProd());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkSnippet();
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1573)
    @Test(description = "Тест успешного добавления товара в корзину из карточки товара", groups = {STF_PROD_S})
    public void successAddItemToCartFromItemCard() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCartByQa(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1574)
    @Test(description = "Тест на изменение кол-ва товаров в корзине", groups = {STF_PROD_S})
    public void successChangeItemQuantityInCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.trainingAddressProd());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkSnippet();
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);

        shop().interactCart().getFirstItem().increaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);

        shop().interactCart().getFirstItem().decreaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1575)
    @Test(description = "Тест на изменение кол-ва товаров в корзине через карточку товара", groups = {STF_PROD_S})
    public void successChangeItemQuantityInCartViaItemCard() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.trainingAddressProd());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().clickOnBuy();
        shop().goToPage();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1576)
    @Test(description = "Тест на удаление товаров из корзины", groups = {STF_PROD_S})
    public void successRemoveItemsFromCart() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCartByQa(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(2616)
    @Test(description = "Добавление/удаление товара из карточки товара", groups = {STF_PROD_S})
    public void testAddedAndRemoveProductFromProductCard() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().checkBuyButtonVisible();
    }

    @CaseIDs(value = {@CaseId(2620), @CaseId(2937), @CaseId(2938)})
    @Test(description = "Многократное добавление и удаление одной позиции", groups = {"regression", "all-cart"})
    public void testMultipleAddAndRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().openFirstProductCardProd();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(3);
        shop().assertAll();
        shop().interactCart().closeCart();

        shop().openFirstProductCardProd();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(2605)
    @Test(description = "Тест успешного добавления товара в пустую корзину", groups = {STF_PROD_S})
    public void testSuccessAddItemInEmptyCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCartProd();
        final var shopProductName = shop().getProductTitleByPositionProd(1);
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2607)
    @Test(description = "Изменение количества единиц товаров в корзине", groups = {STF_PROD_S})
    public void testAddProductsInCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        helper.dropAndFillCart(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var firstItemQuantity = shop().interactCart().getFirstItem().getCount();
        final var startOrderAmount = shop().interactCart().getOrderAmount();
        shop().interactCart().getFirstItem().increaseCount();
        shop().interactCart().getFirstItem().checkSpinnerIsVisible();
        shop().interactCart().getFirstItem().checkSpinnerIsNotVisible();
        shop().refresh();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(firstItemQuantity + 1);
        final var secondOrderAmount = shop().interactCart().getOrderAmount();
        shop().interactCart().checkAmountNotEquals(startOrderAmount, secondOrderAmount);
        shop().assertAll();
    }

    @CaseId(2609)
    @Test(description = "Подтягивание адреса и мердж корзины из профиля при авторизации", groups = {STF_PROD_S})
    public void testAddressAndCartGetFromProfileAuth() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.learningCenter());
        helper.dropAndFillCart(userData, DEFAULT_METRO_MOSCOW_SID);

        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(RestAddresses.Moscow.learningCenter().getFullAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkSnippet();

        final var shopProductName = shop().getProductTitleByPositionProd(1);
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var currentAddress = shop().interactHeader().returnCurrentAddress();
        shop().interactHeader().checkIsSetAddressEqualToInput(RestAddresses.Moscow.learningCenter().getFullAddress(), currentAddress);

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkDeleteAnimationOver();

        final var cartProductName = shop().interactCart().getLastItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2611)
    @Test(description = "Удаление всех товаров в корзине", groups = {STF_PROD_S})
    public void testRemoveRetailerFromCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        helper.dropAndFillCart(userData, DEFAULT_SID, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().getFirstRetailer().removeRetailer();
        shop().interactCart().interactCartModal().confirm();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(2612)
    @Test(description = "Отображение нескольких магазинов в корзине, разбивка товаров по магазинам", groups = {STF_PROD_S})
    public void testMultiplyOrderGroupingProductsByRetailers() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID, 2);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkRetailersCountShouldBe(2);

        var itemsCountInRetailer = shop().interactCart().getRetailerByOrder(1).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 2);

        itemsCountInRetailer = shop().interactCart().getRetailerByOrder(2).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 1);
    }

    @CaseId(2613)
    @Test(description = "Удаление магазина из корзины, при удалении всех его товаров в корзине", groups = {STF_PROD_S})
    public void testAutoRemoveRetailerAfterRemoveAllProducts() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCartMultipleByQA(userData, RestAddresses.Moscow.defaultProdAddress(), DEFAULT_AUCHAN_SID, DEFAULT_METRO_MOSCOW_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkRetailersCountShouldBe(2);

        shop().interactCart().getFirstRetailer().removeAllItemsFromRetailer();
        shop().interactCart().checkRetailerNotVisible("AUCHAN");
    }
}
