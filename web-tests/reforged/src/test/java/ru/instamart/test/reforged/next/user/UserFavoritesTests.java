package ru.instamart.test.reforged.next.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.next.page.StfRouter.shop;
import static ru.instamart.reforged.next.page.StfRouter.userFavorites;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(1263)
    @Test(description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {"smoke", "regression"})
    public void noAccessToFavoritesForUnauthorizedUser() {
        userFavorites().goToPage();
        userFavorites().checkForbiddenPageUrl(userFavorites().pageUrl());
    }

    @CaseId(1265)
    @Test(description = "Проверка пустого списка любимых товаров для нового пользователя", groups = "regression")
    public void noFavoriteItemsByDefault() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkEmptyFavorites();
    }

    @CaseId(1266)
    @Test(description = "Добавление любимого товара из карточки товара и проверка списка", groups = {"smoke", "regression"})
    public void successAddFavoriteOnItemCard() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactProductCard().clickOnClose();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
    }

    @CaseId(1267)
    @Test(description = "Удаление любимого товара из карточки товара и проверка списка", groups = "regression")
    public void successDeleteFavoriteOnItemCard() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 2);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();

        //TODO Разобратся, что за костыль торчит уже больше года, похоже на баг - после удаления кидает на 404
        userFavorites().refresh();

        userFavorites().checkCountChange(userFavorites().getFavoritesCount(), 1);
    }

    @CaseId(1268)
    @Test(description = "Удаление всех любимых товаров", groups = "regression")
    public void successCleanupFavorites() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();

        //TODO Разобратся, что за костыль торчит уже больше года, похоже на баг - после удаления кидает на 404
        userFavorites().refresh();

        userFavorites().checkEmptyFavorites();
    }

    @CaseId(1269)
    @Test(description = "Проверка работоспособности фильтров Любимых товаров", groups = "regression")
    public void successApplyFilters() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 10);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
        userFavorites().checkAllGoodsActive();
        userFavorites().filterInStock();
        userFavorites().checkInStockActive();
        userFavorites().filterOutOfStock();
        userFavorites().checkOutOfStockActive();
    }

    @CaseId(1270)
    @Test(description = "Проверка работоспособности подгрузки страниц в Любимых товарах", groups = "regression")
    public void successShowMoreLoad() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_AUCHAN_SID, 35);

        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().addCookie(CookieFactory.COOKIE_ALERT);
        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();

        final int initCount = userFavorites().getFavoritesCount();
        userFavorites().showMore();
        userFavorites().checkShowMoreNotVisible();
        userFavorites().checkCountLess(initCount, userFavorites().getFavoritesCount());
    }

    @CaseId(1271)
    @Test(description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным", groups = "regression")
    public void successRegAfterAddFavoriteOnCatalog() {
        shop().goToPage();
        shop().checkFirstProductCardIsVisible();
        shop().addFirstItemToFavorite();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1272)
    @Test(description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным", groups = {"smoke", "regression"})
    public void successAuthAfterAddFavoriteOnItemCard() {
        shop().goToPage();
        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1492)
    @Test(description = "Тест добавления товаров в корзину из списка любимых товаров", groups = {"smoke", "regression"})
    public void successAddFavoriteProductToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().addToCartFirstFavoriteItem();

        shop().interactHeader().checkCartNotificationIsVisible();

        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }

    @CaseId(1494)
    @Test(description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров", groups = "regression")
    public void successAddFavoriteProductsFromCardToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 3);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().refreshWithoutBasicAuth();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().clickOnBuy();
        userFavorites().interactProductCart().clickOnClose();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartOpen();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }

    @CaseId(2604)
    @Test(description = "Открывается карточка товара, которого нет в наличии", groups = "regression")
    public void testOpenCartOutOfStockProduct() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addSoldProductToFavorite(userData);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().filterOutOfStock();
        userFavorites().refreshWithoutBasicAuth();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().checkBuyButtonInActive();
    }
}
