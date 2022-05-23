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

import static ru.instamart.kraken.config.EnvironmentProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1571)
    @Test(description = "Тест валидации дефолтной корзины", groups = {"acceptance", "regression"})
    public void successValidateDefaultCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
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
    @Test(description = "Тест успешного добавления товара в корзину неавторизованным юзером", groups = {"acceptance", "regression"})
    public void successAddItemToCartUnauthorized() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusItemToCart("1", "0");
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1573)
    @Test(description = "Тест успешного добавления товара в корзину из карточки товара", groups = {"acceptance", "regression"})
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

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartNotEmpty();
    }

    @CaseId(1574)
    @Test(description = "Тест на изменение кол-ва товаров в корзине", groups = "regression")
    public void successChangeItemQuantityInCart() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusItemToCart("1", "0");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);

        shop().interactCart().getFirstItem().increaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);

        shop().interactCart().getFirstItem().decreaseCount();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1575)
    @Test(description = "Тест на изменение кол-ва товаров в корзине через карточку товара", groups = {"regression"})
    public void successChangeItemQuantityInCartViaItemCard() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
        shop().refreshWithoutBasicAuth();

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().goToPage();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);

        shop().goToPage();
        shop().refresh();

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(2);

        shop().goToPage();
        shop().refreshWithoutBasicAuth();

        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().close();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1576)
    @Test(description = "Тест на удаление товаров из корзины", groups = {"acceptance", "regression"})
    public void successRemoveItemsFromCart() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusItemToCart("1", "0");
        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1577)
    @Test(description = "Тест успешного добавления и удаления товара в корзину из сниппета в каталоге", groups = {"acceptance", "regression"})
    public void successAddItemToCartFromCatalogSnippet() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusItemToCart("1", "0");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartNotEmpty();

        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(1578)
    @Test(description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером", groups = "regression")
    public void successChangeMinOrderSum() {
        final var shoppingCartUser = UserManager.getQaUser();
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusItemToCart("1", "0");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToCart();
        final var firstOrderMinAmount = shop().interactCart().getFirstRetailer().getMinOrderAmount();

        helper.makeAndCompleteOrder(shoppingCartUser, DEFAULT_AUCHAN_SID, 3);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        shop().interactHeader().checkEnteredAddressIsVisible();
        search().checkAddToCartButtonVisible();
        search().clickAddToCartFirstSearchResult();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToCart();
        final var repeatedOrderMinAmount = shop().interactCart().getFirstRetailer().getMinOrderAmount();

        shop().interactCart().checkFirstMinAmountLessThanRepeated(firstOrderMinAmount, repeatedOrderMinAmount);
    }

    @CaseId(2616)
    @Test(description = "Добавление/удаление товара из карточки товара", groups = "regression")
    public void testAddedAndRemoveProductFromProductCard() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().checkProductCardVisible();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().checkBuyButton();
    }

    @CaseId(2618)
    @Test(description = "Добавление/удаление товара из раздела 'Скидки'", groups = "regression")
    public void testAddProductFromSale() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCategoryMenu();
        shop().waitPageLoad();
        shop().interactCategoryMenu().clickToFirstLevelCategoryByName("Скидки");

        seo().openFirstProductCardOnDepartment();
        seo().interactProductCard().clickOnBuy();
        seo().interactProductCard().decreaseItemCount();
        seo().interactProductCard().closeByEsc();
        seo().interactHeader().clickToCart();
        seo().interactCart().checkCartEmpty();
    }

    @CaseId(2619)
    @Test(description = "Добавление товара после изменения адреса доставки", groups = "regression")
    public void testAddProductAfterChangeAddress() {
        var userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, DEFAULT_SID);
        helper.setAddress(userData, RestAddresses.NizhnyNovgorod.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(5);
        shop().assertAll();
    }

    @CaseIDs(value = {@CaseId(2620), @CaseId(2937), @CaseId(2938)})
    @Test(description = "Многократное добавление и удаление одной позиции", groups = "regression")
    public void testMultipleAddAndRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().compareItemQuantityInCart(3);
        shop().assertAll();
        shop().interactCart().closeCart();

        shop().openFirstProductCard();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().decreaseItemCount();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().checkCartEmpty();
    }

    @CaseId(2605)
    @Test(description = "Тест успешного добавления товара в пустую корзину", groups = "regression")
    public void testSuccessAddItemInEmptyCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusItemToCart("1", "0");
        final var shopProductName = shop().getProductTitle("1", "0");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2607)
    @Test(description = "Изменение количества единиц товаров в корзине", groups = "regression")
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
    @Test(description = "Подтягивание адреса и мердж корзины из профиля при авторизации", groups = "regression")
    public void testAddressAndCartGetFromProfileAuth() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.learningCenter());
        helper.dropAndFillCart(userData, 1);

        shop().goToPage();
        shop().openAddressFrame();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var shopProductName = shop().getProductTitle("0", "1");
        shop().plusItemToCart("0", "1");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        final var currentAddress = shop().interactHeader().returnCurrentAddress();
        shop().interactHeader().checkIsSetAddressEqualToInput(Addresses.Moscow.defaultAddress(), currentAddress);

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().deleteItem();
        shop().interactCart().checkDeleteAnimationOver();

        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2610)
    @Test(description = "Удаление позиции товара", groups = "regression")
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
    @Test(description = "Тест успешного добавления товара в корзину неавторизованным юзером", groups = "regression")
    public void testAddToCartNonAuthUser() {
        shop().goToPage();
        shop().openAddressFrame();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();

        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusItemToCart("0", "0");
        final var shopProductName = shop().getProductTitle("0", "0");
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, shopProductName);
    }

    @CaseId(2611)
    @Test(description = "Удаление всех товаров в корзине", groups = "regression")
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
    @Test(description = "Отображение нескольких магазинов в корзине, разбивка товаров по магазинам", groups = "regression")
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
    @Test(description = "Удаление магазина из корзины, при удалении всех его товаров в корзине", groups = "regression")
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
        shop().interactCart().checkRetailersCountShouldBe(1);
    }

    @CaseId(2617)
    @Test(description = "Добавление/удаление товара из категории", groups = "regression")
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
    @Test(description = "Добавление товара в корзину из seo-каталога", groups = "regression")
    public void testAddProductFromSEOCategory() {
        seoIncognito().goToPage();
        seoIncognito().checkProductGridVisible();

        seoIncognito().hoverFirstProduct();
        seoIncognito().checkAddToCartButtonVisible();

        seoIncognito().clickAddToCartButton();
        seoIncognito().interactHeader().interactAddress().checkAddressModalVisible();
    }

    @CaseId(2615)
    @Test(description = "Добавление товара в корзину из блока рекомендаций на карточке товара", groups = "regression")
    public void successAddToCardFromProductCardRecommendations() {
        var userData = UserManager.getQaUser();
        helper.setAddressBySid(userData, DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openSitePage("metro/maslo-aro-extra-virgin-olivkovoe?sid=81");
        shop().interactProductCard().checkProductCardVisible();
        final var firstProductFromRecs = shop().interactProductCard().getFirstProductNameFromRecs();

        shop().interactProductCard().plusFirstProductToCart();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();

        final var cartProductName = shop().interactCart().getFirstItem().getName();
        shop().interactCart().compareProductNameInCart(cartProductName, firstProductFromRecs);
    }
}
