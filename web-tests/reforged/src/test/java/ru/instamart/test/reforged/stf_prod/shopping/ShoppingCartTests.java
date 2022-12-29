package ru.instamart.test.reforged.stf_prod.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("1571")
    @Issue("B2C-10717")
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

    @TmsLink("1572")
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
        shop().plusFirstItemToCartWithScrollDown();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @TmsLink("1573")
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
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @TmsLink("1574")
    @Test(description = "Тест на изменение кол-ва товаров в корзине", groups = {STF_PROD_S})
    public void successChangeItemQuantityInCart() {
        home().goToPage();
        home().fillAddressInLanding(Addresses.Moscow.trainingAddressProd());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();

        home().clickOnStoreWithSid(DEFAULT_METRO_MOSCOW_SID);

        shop().waitPageLoad();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartWithScrollDown();
        shop().checkItemQuantityVisible();
        var itemQuantity = StringUtil.stringToDouble(shop().getFirstItemQuantity());
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(StringUtil.doubleToString(itemQuantity));

        shop().interactCart().getFirstItem().increaseCount();
        shop().waitPageLoad();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(StringUtil.doubleToString(itemQuantity * 2));
        shop().checkItemQuantity(StringUtil.doubleToString(itemQuantity * 2));

        shop().interactCart().getFirstItem().decreaseCount();
        shop().waitPageLoad();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(StringUtil.doubleToString(itemQuantity));
        shop().checkItemQuantity(StringUtil.doubleToString(itemQuantity));
    }

    @TmsLink("1575")
    @Test(description = "Тест на изменение кол-ва товаров в корзине через карточку товара", groups = {STF_PROD_S})
    public void successChangeItemQuantityInCartViaItemCard() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(RestAddresses.Moscow.learningCenter().getFullAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();
        home().checkStoreCardsAnimationFinished();
        home().clickOnStoreWithSid(DEFAULT_SID);

        shop().waitPageLoad();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().checkSnippet();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().checkQuantityInputVisible();
        var itemQuantity = StringUtil.stringToDouble(shop().interactProductCard().getProductQuantity());
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(itemQuantity);
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkItemQuantity(StringUtil.doubleToString(itemQuantity));
        shop().interactProductCard().increaseItemCount();
        shop().waitPageLoad();
        shop().interactProductCard().checkItemQuantity(StringUtil.doubleToString(itemQuantity * 2));
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(StringUtil.doubleToString(itemQuantity * 2));
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkItemQuantity(StringUtil.doubleToString(itemQuantity * 2));
        shop().interactProductCard().decreaseItemCount();
        shop().waitPageLoad();
        shop().interactProductCard().checkItemQuantity(StringUtil.doubleToString(itemQuantity));
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(StringUtil.doubleToString(itemQuantity));
    }

    @TmsLink("1576")
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
        shop().plusFirstItemToCartWithScrollDown();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().getFirstItem().checkReturnDeletedButtonVisible();

        shop().interactCart().checkCartEmpty();
    }

    @TmsLink("2616")
    @Test(description = "Добавление/удаление товара из карточки товара", groups = {STF_PROD_S})
    public void testAddedAndRemoveProductFromProductCard() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().checkBuyButtonVisible();
    }

    @TmsLinks(value = {@TmsLink("2620"), @TmsLink("2937"), @TmsLink("2938")})
    @Test(description = "Многократное добавление и удаление одной позиции", groups = {"regression", "all-cart"})
    public void testMultipleAddAndRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(3);
        shop().interactCart().closeCart();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @TmsLink("2605")
    @Test(description = "Тест успешного добавления товара в пустую корзину", groups = {STF_PROD_S})
    public void testSuccessAddItemInEmptyCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCartWithScrollDown();
        final var shopProductName = shop().getProductTitleByPosition(1);
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @TmsLink("2607")
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
    }

    @TmsLink("2609")
    @Test(description = "Подтягивание адреса и мердж корзины из профиля при авторизации", groups = {STF_PROD_S})
    public void testAddressAndCartGetFromProfileAuth() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.learningCenter());
        helper.dropAndFillCart(userData, DEFAULT_METRO_MOSCOW_SID);

        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(RestAddresses.Moscow.learningCenter().getFullAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();
        home().checkStoreCardsAnimationFinished();
        home().clickOnStoreWithSid(DEFAULT_METRO_MOSCOW_SID);

        shop().waitPageLoad();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().checkSnippet();

        final var shopProductName = shop().getProductTitleByPositionWithScrollDown(1);
        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var currentAddress = shop().interactHeader().returnCurrentAddress();
        shop().interactHeader().checkIsSetAddressEqualToInput(RestAddresses.Moscow.learningCenter().getFullAddress(), currentAddress);

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        final var cartProductName = shop().interactCart().getLastItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @TmsLink("2611")
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
        shop().interactCart().interactCartModal().checkModalIsOpen();
        shop().interactCart().interactCartModal().confirm();
        shop().interactCart().checkCartEmpty();
    }

    @TmsLink("2612")
    @Test(description = "Отображение нескольких магазинов в корзине, разбивка товаров по магазинам", groups = {STF_PROD_S})
    public void testMultiplyOrderGroupingProductsByRetailers() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_AUCHAN_SID, 1);
        helper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().checkSnippet();
        shop().plusFirstItemToCartWithScrollDown();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkRetailersCountShouldBe(2);

        var itemsCountInRetailer = shop().interactCart().getRetailerByOrder(1).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 1);

        itemsCountInRetailer = shop().interactCart().getRetailerByOrder(2).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 1);
    }

    @TmsLink("2613")
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
        shop().interactCart().checkRetailerNotVisible("АШАН");
    }
}
