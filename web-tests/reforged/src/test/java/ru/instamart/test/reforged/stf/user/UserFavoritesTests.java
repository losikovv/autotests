package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(1263)
    @Test(description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {"smoke", "acceptance","regression"})
    public void noAccessToFavoritesForUnauthorizedUser() {
        userFavorites().goToPage();
        userFavorites().checkForbiddenPageUrl(userFavorites().pageUrl());
    }

    @CaseId(1265)
    @Test(description = "Проверка пустого списка любимых товаров для нового пользователя", groups = {"acceptance","regression"})
    public void noFavoriteItemsByDefault() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        userFavorites().goToPage();
        userFavorites().checkEmptyFavorites();
    }

    @Issue("STF-8253")
    @CaseId(1266)
    @Test(description = "Добавление любимого товара из карточки товара и проверка списка", groups = {"smoke", "regression"})
    public void successAddFavoriteOnItemCard() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
    }

    @Issue("STF-8253")
    @CaseId(1267)
    @Test(description = "Удаление любимого товара из карточки товара и проверка списка", groups = "regression")
    public void successDeleteFavoriteOnItemCard() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 2);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();
        userFavorites().refresh();

        userFavorites().checkCountChange(userFavorites().getFavoritesCount(), 1);
    }

    @Issue("STF-8253")
    @CaseId(1268)
    @Test(description = "Удаление всех любимых товаров", groups = "regression")
    public void successCleanupFavorites() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();
        userFavorites().refresh();
        userFavorites().checkEmptyFavorites();
    }

    @Issue("STF-8253")
    @CaseId(1269)
    @Test(description = "Проверка работоспособности фильтров Любимых товаров", groups = {"acceptance", "regression"})
    public void successApplyFilters() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 10);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
        userFavorites().checkAllGoodsActive();
        userFavorites().filterInStock();
        userFavorites().checkInStockActive();
        userFavorites().filterOutOfStock();
        userFavorites().checkOutOfStockActive();
    }

    @Issue("STF-8253")
    @CaseId(1270)
    @Test(description = "Проверка работоспособности подгрузки страниц в Любимых товарах", groups = "regression")
    public void successShowMoreLoad() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 50);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
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
        shop().addFirstItemToFavorite();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1272)
    @Test(description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным", groups = {"smoke", "regression"})
    public void successAuthAfterAddFavoriteOnItemCard() {
        shop().goToPage();
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER);
        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().goToPage();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @Issue("STF-8253")
    @CaseId(1492)
    @Test(description = "Тест добавления товаров в корзину из списка любимых товаров", groups = {"smoke", "regression"})
    public void successAddFavoriteProductToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 3);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().addToCartFirstFavoriteItem();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }

    @Issue("STF-8253")
    @CaseId(1494)
    @Test(description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров", groups = {"acceptance", "regression"})
    public void successAddFavoriteProductsFromCardToCart() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        apiHelper.addFavorites(userData, EnvironmentProperties.DEFAULT_SID, 3);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().refreshWithoutBasicAuth();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().clickOnBuy();
        userFavorites().interactProductCart().close();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartOpen();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }
}
