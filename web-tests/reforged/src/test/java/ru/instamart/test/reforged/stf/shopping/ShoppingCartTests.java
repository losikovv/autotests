package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1571)
    @Test(description = "Тест валидации дефолтной корзины", groups = {REGRESSION_STF, "all-cart"})
    public void successValidateDefaultCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
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
    @Test(description = "Тест успешного добавления товара в корзину неавторизованным юзером", groups = {REGRESSION_STF, "all-cart"})
    public void successAddItemToCartUnauthorized() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1573)
    @Test(description = "Тест успешного добавления товара в корзину из карточки товара", groups = {REGRESSION_STF, "all-cart"})
    public void successAddItemToCartFromItemCard() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        //TODO: Костыль из-за бейсик авторизации
        shop().refreshWithoutBasicAuth();

        shop().openFirstProductCardProd();
        shop().checkFirstProductCardIsVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1574)
    @Test(description = "Тест на изменение кол-ва товаров в корзине", groups = {REGRESSION_STF, "all-cart"})
    public void successChangeItemQuantityInCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        var orderAmount = shop().interactCart().getOrderAmount();

        shop().interactCart().getFirstItem().increaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);
        shop().interactCart().getFirstItem().checkSpinnerIsVisible();
        shop().interactCart().getFirstItem().checkSpinnerIsNotVisible();
        shop().interactCart().checkAmountNotEquals(orderAmount, shop().interactCart().getOrderAmount());

        shop().interactCart().getFirstItem().decreaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        shop().interactCart().getFirstItem().checkSpinnerIsVisible();
        shop().interactCart().getFirstItem().checkSpinnerIsNotVisible();
        shop().interactCart().checkAmountEquals(orderAmount, shop().interactCart().getOrderAmount());
        shop().assertAll();
    }

    @CaseId(1575)
    @Test(description = "Тест на изменение кол-ва товаров в корзине через карточку товара", groups = {REGRESSION_STF, "all-cart"})
    public void successChangeItemQuantityInCartViaItemCard() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCardProd();
        shop().interactProductCard().clickOnBuy();
        shop().goToPage();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);

        shop().goToPage();
        shop().refresh();

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCardProd();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);

        shop().goToPage();
        shop().refreshWithoutBasicAuth();

        shop().checkFirstProductCardIsVisible();
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
    @Test(description = "Тест на удаление товаров из корзины", groups = {REGRESSION_STF, "all-cart"})
    public void successRemoveItemsFromCart() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCartProd();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1577)
    @Test(description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге", groups = {REGRESSION_STF, "all-cart"})
    public void successAddItemToCartFromCatalogSnippet() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1578)
    @Test(description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером", groups = {REGRESSION_STF, "all-cart"})
    public void successChangeMinOrderSum() {
        final var shoppingCartUser = UserManager.getQaUser();
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToCart();
        final var firstOrderMinAmount = shop().interactCart().getFirstRetailer().getMinOrderAmount();

        helper.dropAndFillCart(shoppingCartUser, DEFAULT_AUCHAN_SID);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setSlot().setLastActiveSlot();
        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        final var repeatedOrderMinAmount = shop().interactCart().getFirstRetailer().getMinOrderAmount();

        shop().interactCart().checkFirstMinAmountLessThanRepeated(firstOrderMinAmount, repeatedOrderMinAmount);
    }

    @CaseId(2616)
    @Test(description = "Добавление/удаление товара из карточки товара", groups = {REGRESSION_STF, "all-cart"})
    public void testAddedAndRemoveProductFromProductCard() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCardProd();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().checkBuyButtonVisible();
    }

    @CaseId(2618)
    @Test(description = "Добавление/удаление товара из раздела 'Скидки'", groups = {REGRESSION_STF, "all-cart"})
    public void testAddProductFromSale() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().checkSnippet();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Скидки");

        seo().openFirstProductCardOnDepartment();
        seo().interactProductCard().clickOnBuy();
        seo().interactProductCard().decreaseItemCount();
        seo().interactProductCard().clickOnClose();
        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartEmpty();
    }

    @CaseId(2619)
    @Test(description = "Добавление товара после изменения адреса доставки", groups = {REGRESSION_STF, "all-cart"})
    public void testAddProductAfterChangeAddress() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCartByOneProduct(userData, DEFAULT_SID, 5);
        helper.setAddress(userData, RestAddresses.NizhnyNovgorod.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCardProd();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(6);
        shop().assertAll();
    }

    @CaseIDs(value = {@CaseId(2620), @CaseId(2937), @CaseId(2938)})
    @Test(description = "Многократное добавление и удаление одной позиции", groups = {REGRESSION_STF, "all-cart"})
    public void testMultipleAddAndRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

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
    @Test(description = "Тест успешного добавления товара в пустую корзину", groups = {REGRESSION_STF, "all-cart"})
    public void testSuccessAddItemInEmptyCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

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
    @Test(description = "Изменение количества единиц товаров в корзине", groups = {REGRESSION_STF, "all-cart"})
    public void testAddProductsInCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
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
    @Test(description = "Подтягивание адреса и мердж корзины из профиля при авторизации", groups = {REGRESSION_STF, "all-cart"})
    public void testAddressAndCartGetFromProfileAuth() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.learningCenter());
        helper.dropAndFillCart(userData, 1);

        shop().goToPage();
        shop().openAddressFrame();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().clickSave();
        shop().interactAddressLarge().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var shopProductName = shop().getProductTitleByPositionProd(2);
        shop().plusItemToCartByPosition(2);
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var currentAddress = shop().interactHeader().returnCurrentAddress();
        shop().interactHeader().checkIsSetAddressEqualToInput(Addresses.Moscow.defaultAddress(), currentAddress);

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        final var cartProductName = shop().interactCart().getLastItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2610)
    @Test(description = "Удаление позиции товара", groups = {REGRESSION_STF, "all-cart"})
    public void testRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        helper.dropAndFillCart(userData, 1, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var startOrderAmount = shop().interactCart().getOrderAmount();
        final var startProductsQuantity = shop().interactCart().getFirstRetailer().getItemsCountInList();
        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkDeleteAnimationOver();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().refresh();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var secondOrderAmount = shop().interactCart().getOrderAmount();
        shop().interactCart().checkAmountNotEquals(startOrderAmount, secondOrderAmount);
        shop().interactCart().getFirstRetailer().compareItemsInCart(startProductsQuantity - 1);
        shop().assertAll();
    }

    @CaseId(1572)
    @Test(description = "Тест успешного добавления товара в корзину неавторизованным юзером", groups = {REGRESSION_STF, "all-cart"})
    public void testAddToCartNonAuthUser() {
        shop().goToPage();
        shop().openAddressFrame();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().clickSave();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCartProd();
        final var shopProductName = shop().getProductTitleByPositionProd(1);
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2611)
    @Test(description = "Удаление всех товаров в корзине", groups = {REGRESSION_STF, "all-cart"})
    public void testRemoveRetailerFromCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
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
    @Test(description = "Отображение нескольких магазинов в корзине, разбивка товаров по магазинам", groups = {REGRESSION_STF, "all-cart"})
    public void testMultiplyOrderGroupingProductsByRetailers() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCartMultiple(userData, RestAddresses.Moscow.defaultAddress(), DEFAULT_METRO_MOSCOW_SID, 2, DEFAULT_AUCHAN_SID, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        var itemsCountInRetailer = shop().interactCart().getRetailerByOrder(1).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 2);

        itemsCountInRetailer = shop().interactCart().getRetailerByOrder(2).getItemsCountInList();
        shop().interactCart().checkItemsCount(itemsCountInRetailer, 3);
    }

    @CaseId(2613)
    @Test(description = "Удаление магазина из корзины, при удалении всех его товаров в корзине", groups = {REGRESSION_STF, "all-cart"})
    public void testAutoRemoveRetailerAfterRemoveAllProducts() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCartMultiple(userData,
                RestAddresses.Moscow.defaultAddress(),
                DEFAULT_METRO_MOSCOW_SID,
                2,
                DEFAULT_AUCHAN_SID,
                3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkRetailersCountShouldBe(2);

        shop().interactCart().getFirstRetailer().removeAllItemsFromRetailer();
        shop().interactCart().checkRetailerNotVisible("METRO");
    }

    @CaseId(2617)
    @Test(description = "Добавление/удаление товара из категории", groups = {REGRESSION_STF, "all-cart"})
    public void testAddRemoveProductFromCategory() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Бакалея");

        seo().interactHeader().checkEnteredAddressIsVisible();
        seo().addFirstProductOnDepartmentToCart();

        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartOpen();
        seo().interactCart().checkCartNotEmpty();

        seo().interactCart().closeCart();
        seo().removeFirstProductOnDepartmentFromCart();

        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartOpen();
        seo().interactCart().checkCartEmpty();
    }

    @CaseId(3042)
    @Test(description = "Добавление товара в корзину из seo-каталога", groups = {REGRESSION_STF, "all-cart"})
    public void testAddProductFromSEOCategory() {
        seoIncognito().goToPage();
        seoIncognito().checkProductGridVisible();

        seoIncognito().hoverFirstProduct();
        seoIncognito().checkAddToCartButtonVisible();

        seoIncognito().clickAddToCartButton();
        seoIncognito().interactHeader().interactAddress().checkAddressModalVisible();
    }

    @CaseId(2615)
    @Test(description = "Добавление товара в корзину из блока рекомендаций на карточке товара", groups = {REGRESSION_STF, "all-cart"})
    public void successAddToCardFromProductCardRecommendations() {
        var userData = UserManager.getQaUser();
        helper.setAddressBySid(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().goToPage("metro/maslo-aro-extra-virgin-olivkovoe?sid=81");
        shop().interactProductCard().checkProductCardVisible();
        final var firstProductFromRecs = shop().interactProductCard().getFirstProductNameFromRecs();

        shop().interactProductCard().plusFirstProductToCart();
        shop().interactProductCard().clickOnClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, firstProductFromRecs);
    }
}
