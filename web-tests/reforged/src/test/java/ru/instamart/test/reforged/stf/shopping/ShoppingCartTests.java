package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Основные тесты корзины")
public final class ShoppingCartTests extends BaseTest {

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

        shop().plusFirstItemToCart();
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
    @Test(description = "Тест на изменение кол-ва товаров в корзине", groups = "regression" )
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

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().goToPage();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareFirstItemQuantityInCart(1);

        shop().interactCart().increaseCount();
        shop().interactCart().compareFirstItemQuantityInCart(2);

        shop().interactCart().decreaseCount();
        shop().interactCart().compareFirstItemQuantityInCart(1);
        shop().assertAll();
    }

    @CaseId(1575)
    @Test(description = "Тест на изменение кол-ва товаров в корзине через карточку товара", groups = {"regression"})
    public void successChangeItemQuantityInCartViaItemCard() {
        shop().goToPage();
        shop().checkSpinnerIsNotVisible();
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

        shop().checkSpinnerIsNotVisible();
        shop().checkFirstProductCardIsVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().goToPage();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().compareFirstItemQuantityInCart(1);

        shop().goToPage();
        shop().refresh();

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
        shop().refreshWithoutBasicAuth();

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

    //TODO: Требует уточнения, сейчас если не отменить\завершить первый заказ, возможен дозаказ.
    // После успешного первого заказа, минималка не меняется, для метро
    // ATST-872
    @Skip
    @CaseId(1578)
    @Test(description = "Тест на изменение суммы минимального заказа после первого заказ новым юзером", groups = "regression" )
    public void successChangeMinOrderSum() {
        final UserData shoppingCartUser = UserManager.getQaUser();
        helper.dropCart(shoppingCartUser);
        helper.setAddress(shoppingCartUser, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(shoppingCartUser);
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

    @CaseId(2616)
    @Test(description = "Добавление/удаление товара из карточки товара", groups = "regression")
    public void testAddedAndRemoveProductFromProductCard() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactAuthModal().checkModalIsNotVisible();

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

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);

        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToCategoryMenu();
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
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();
        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().compareFirstItemQuantityInCart(5);
    }

    @CaseId(2620) //2937, 2938
    @Test(description = "Многократное добавление и удаление одной позиции", groups = "regression")
    public void testMultipleAddAndRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().increaseItemCount();
        shop().interactProductCard().close();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().compareFirstItemQuantityInCart(3);
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

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().plusFirstItemToCart();
        final var productName = shop().returnFirstProductTitle();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().compareProductNameInCart(productName);
    }

    @CaseId(2607)
    @Test(description = "Изменение количества единиц товаров в корзине", groups = "regression")
    public void testAddProductsInCart() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        helper.dropAndFillCart(userData, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var firstItemQuantity = shop().interactCart().returnFirstItemQuantity();
        final var startOrderAmount = shop().interactCart().returnOrderAmount();
        shop().interactCart().increaseCount();
        shop().interactCart().checkSpinnerIsVisible();
        shop().interactCart().checkSpinnerIsNotVisible();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().compareFirstItemQuantityInCart(firstItemQuantity+1);
        final var secondOrderAmount = shop().interactCart().returnOrderAmount();
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

        final var productName = shop().returnSecondProductTitleNonLogin();
        shop().plusSecondItemToCartNonLogin();
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
        shop().interactCart().deleteFirstItem();
        shop().interactCart().checkDeleteAnimationOver();

        shop().interactCart().compareProductNameInCart(productName);
    }

    @CaseId(2610)
    @Test(description = "Удаление позиции товара", groups = "regression")
    public void testRemoveProduct() {
        var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        helper.dropAndFillCart(userData, 1, 3);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var startOrderAmount = shop().interactCart().returnOrderAmount();
        final var startProductsQuantity = shop().interactCart().returnUniqueItemsQuantity();
        shop().interactCart().deleteFirstItem();
        shop().interactCart().checkDeleteAnimationOver();
        shop().interactCart().closeCart();
        shop().interactCart().checkCartClose();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        final var secondOrderAmount = shop().interactCart().returnOrderAmount();
        shop().interactCart().checkAmountNotEquals(startOrderAmount, secondOrderAmount);
        shop().interactCart().compareItemsInCart(startProductsQuantity-1);
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

        shop().plusFirstItemToCartNonLogin();
        final var productName = shop().returnFirstProductTitleNonLogin();
        shop().interactHeader().checkCartNotificationIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().compareProductNameInCart(productName);
    }
}
