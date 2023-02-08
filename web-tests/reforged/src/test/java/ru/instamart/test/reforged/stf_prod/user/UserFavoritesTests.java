package ru.instamart.test.reforged.stf_prod.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;
import static ru.instamart.reforged.stf.page.StfRouter.userFavorites;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @TmsLink("1263")
    @Test(description = "Тест недоступности страницы любимых товаров неавторизованному юзеру", groups = {STF_PROD_S})
    public void noAccessToFavoritesForUnauthorizedUser() {
        userFavorites().goToPage();
        userFavorites().checkForbiddenPageUrl(userFavorites().pageUrl());
    }

    @TmsLink("1265")
    @Test(description = "Проверка пустого списка любимых товаров для нового пользователя", groups = {STF_PROD_S})
    public void noFavoriteItemsByDefault() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkEmptyFavoritesProd();
    }

    @TmsLink("1266")
    @Test(description = "Добавление любимого товара из карточки товара и проверка списка", groups = {STF_PROD_S})
    public void successAddFavoriteOnItemCard() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactProductCard().clickOnClose();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavoritesProd();
    }

    @TmsLink("1267")
    @Test(description = "Удаление любимого товара из карточки товара и проверка списка", groups = {STF_PROD_S})
    public void successDeleteFavoriteOnItemCard() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        apiHelper.addFavorites(userData, DEFAULT_SID, 2);

        userFavorites().goToPageWithAuth(userData);
        userFavorites().removeFirstFavoriteItem();

        userFavorites().checkCountChange(userFavorites().getFavoritesCount(), 1);
    }

    @TmsLink("1268")
    @Test(description = "Удаление всех любимых товаров", groups = {STF_PROD_S})
    public void successCleanupFavorites() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        apiHelper.addFavorites(userData, DEFAULT_SID, 1);

        userFavorites().goToPageWithAuth(userData);
        userFavorites().removeFirstFavoriteItem();
        userFavorites().checkEmptyFavoritesProd();
    }

    @TmsLink("1270")
    @Test(description = "Проверка работоспособности подгрузки товаров по мере прокрутки списка в Любимых товарах", groups = {STF_PROD_S})
    public void successShowMoreLoad() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        apiHelper.addFavorites(userData, DEFAULT_SID, 35);

        userFavorites().goToPageWithAuth(userData);
        userFavorites().checkNotEmptyFavoritesProd();

        final int initCount = userFavorites().getFavoritesCount();
        userFavorites().scrollDown();
        userFavorites().checkRequestsWasLoad();
        userFavorites().checkSpinnerNotVisible();
        userFavorites().checkCountLess(initCount, userFavorites().getFavoritesCount());
    }

    @TmsLink("1272")
    @Test(description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {STF_PROD_S})
    public void successAuthAfterAddFavoriteOnItemCard() {
        shop().goToPage(DEFAULT_SID);
        shop().openFirstNonRecommendationsProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @TmsLink("1492")
    @Test(description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {STF_PROD_S})
    public void successAddFavoriteProductToCart() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());
        apiHelper.addFavorites(userData, DEFAULT_SID, 3);

        userFavorites().goToPageWithAuth(userData);
        userFavorites().interactHeader().checkEnteredAddressIsVisible();
        userFavorites().addToCartFirstFavoriteItem();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }
}
