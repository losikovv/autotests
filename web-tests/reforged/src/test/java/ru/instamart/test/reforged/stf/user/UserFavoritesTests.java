package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_AUCHAN_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @TmsLink("1263")
    @Test(description = "Тест недоступности страницы любимых товаров неавторизованному юзеру", groups = {REGRESSION_STF, SMOKE_STF})
    public void noAccessToFavoritesForUnauthorizedUser() {
        userFavorites().goToPage();
        userFavorites().checkForbiddenPageUrl(userFavorites().pageUrl());
    }

    @TmsLink("1265")
    @Test(description = "Проверка пустого списка любимых товаров для нового пользователя", groups = {REGRESSION_STF, SMOKE_STF})
    public void noFavoriteItemsByDefault() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkEmptyFavorites();
    }

    @TmsLink("1266")
    @Test(description = "Добавление любимого товара из карточки товара и проверка списка", groups = {REGRESSION_STF, SMOKE_STF})
    public void successAddFavoriteOnItemCard() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactProductCard().clickOnClose();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
    }

    @TmsLink("1267")
    @Test(description = "Удаление любимого товара из карточки товара и проверка списка", groups = {REGRESSION_STF, SMOKE_STF})
    public void successDeleteFavoriteOnItemCard() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, DEFAULT_AUCHAN_SID, 2);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();
        userFavorites().refresh();

        userFavorites().checkCountChange(1, userFavorites().getFavoritesCount());
    }

    @TmsLink("1268")
    @Test(description = "Удаление всех любимых товаров", groups = {REGRESSION_STF, SMOKE_STF})
    public void successCleanupFavorites() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, DEFAULT_AUCHAN_SID, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();
        userFavorites().refresh();
        userFavorites().checkEmptyFavorites();
    }

    @TmsLink("1270")
    @Test(description = "Проверка работоспособности подгрузки товаров по мере прокрутки списка в Любимых товарах", groups = {REGRESSION_STF, SMOKE_STF})
    public void successShowMoreLoad() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, DEFAULT_AUCHAN_SID, 35);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().waitPageLoad();
        userFavorites().checkNotEmptyFavorites();

        final int initCount = userFavorites().getFavoritesCount();
        userFavorites().scrollDown();
        userFavorites().waitPageLoad();
        userFavorites().checkCountLess(initCount, userFavorites().getFavoritesCount());
    }

    @TmsLink("1271")
    @Test(description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным", groups = REGRESSION_STF)
    public void successRegAfterAddFavoriteOnCatalog() {
        shop().goToPage();
        shop().addFirstItemToFavorite();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @TmsLink("1272")
    @Test(description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным", groups = {REGRESSION_STF, SMOKE_STF})
    public void successAuthAfterAddFavoriteOnItemCard() {
        shop().goToPage();
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @TmsLink("1492")
    @Test(description = "Тест добавления товаров в корзину из списка любимых товаров", groups = {REGRESSION_STF, SMOKE_STF})
    public void successAddFavoriteProductToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.getDefaultAddress());
        apiHelper.addFavorites(userData, DEFAULT_AUCHAN_SID, 3);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().checkDeliveryStoresContainerVisible();

        home().clickOnStoreWithSid(DEFAULT_AUCHAN_SID);

        userFavorites().goToPage();
        userFavorites().interactHeader().checkEnteredAddressIsVisible();
        userFavorites().addToCartFirstFavoriteItem();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }

    @TmsLink("1494")
    @Test(description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров", groups = REGRESSION_STF)
    public void successAddFavoriteProductsFromCardToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, DEFAULT_AUCHAN_SID, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().interactHeader().checkEnteredAddressIsVisible();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().clickOnBuy();
        userFavorites().interactProductCart().clickOnClose();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartOpen();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }

    @TmsLink("2604")
    @Test(description = "Открывается карточка товара, которого нет в наличии", groups = REGRESSION_STF)
    public void testOpenCartOutOfStockProduct() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addSoldProductToFavorite(userData);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().interactHeader().checkEnteredAddressIsVisible();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().checkBuyButtonInactive();
    }
}
