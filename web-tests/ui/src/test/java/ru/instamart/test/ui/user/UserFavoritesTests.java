package ru.instamart.test.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.checkpoint.favorite.FavoriteItemsCheckpoints;
import ru.instamart.ui.checkpoint.users.UsersAuthorizationCheckpoints;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests extends TestBase implements FavoriteItemsCheckpoints, UsersAuthorizationCheckpoints {

    private static final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeMethod(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void quickLogout() {
        AppManager.closeWebDriver();
    }

    @CaseId(1263)
    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {
            "sbermarket-Ui-smoke",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAccessToFavoritesForUnauthorizedUser() {
        baseChecks.checkPageIsUnavailable(Pages.UserProfile.favorites());
    }

    @Issue(value="STF-6773")
    @CaseId(1264)
    @Test(  description = "Переход в любимые товары по кнопке, новый пользователь",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-acceptance","metro-regression",
                    "sbermarket-Ui-smoke","testing"}
    )
    @Flaky
    public void successOpenFavorites() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        Shop.Favorites.open();
        baseChecks.checkPageIsAvailable();
        checkIsFavoriteOpen();
    }

    @CaseId(1265)
    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noFavoriteItemsByDefault() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);
        Shop.Favorites.openFavorites();
        checkFavoriteIsEmpty();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1266)
    @Test(  description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteOnItemCard() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);

        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Shop.Favorites.openFavorites();
        checkFavoriteIsNotEmpty();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1267)
    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successDeleteFavoriteOnItemCard() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);

        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Shop.Favorites.openFavorites();
        Shop.Favorites.removeFavorite();

        checkFavoriteIsEmpty();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1268)
    @Test(  description = "Удаление всех любимых товаров",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-regression",
                    "sbermarket-regression"
            }
    )
    public void successCleanupFavorites() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);

        Shop.Search.searchItem("молоко");
        Shop.Search.addToFavorites();

        Shop.Search.searchItem("бананы");
        Shop.Search.addToFavorites();

        Shop.Search.searchItem("яйца");
        Shop.Search.addToFavorites();

        Shop.Search.searchItem("хлеб");
        Shop.Search.addToFavorites();

        Shop.Favorites.openFavorites();
        Shop.Favorites.cleanFavorites();
        checkFavoriteIsEmpty();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1269)
    @Test(  description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    )
    public void successApplyFilters() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        Shop.AuthModal.hitSberIdButton();
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        checkIsUserAuthorized("Не работает авторизация через Sber ID");

        Shop.Favorites.openFavorites();
        checkFavoriteIsNotEmpty();

        Shop.Favorites.applyFilterInStock();
        checkFavoriteFilter("inStock", "В наличии");

        Shop.Favorites.applyFilterNotInStock();
        checkFavoriteFilter("outOfStock", "Нет в наличии");

        Shop.Favorites.applyFilterAllItems();
        checkFavoriteFilter("all", "Все товары");

        assertAll();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1270)
    @Test(  description = "Проверка работоспособности подгрузки страниц в Любимых товарах",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successShowMoreLoad() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        Shop.AuthModal.hitSberIdButton();
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        checkIsUserAuthorized("Не работает авторизация через Sber ID");

        Shop.Favorites.openFavorites();
        checkFavoriteIsNotEmpty();

        final int initCount = Shop.Favorites.count();

        kraken.perform().scrollToTheBottom();

        Shop.Favorites.showMore();

        checkShowMoreNotDisplayed();
        checkCountChange(initCount, Shop.Favorites.count());
    }

    @CaseId(1271)
    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        kraken.get().page(Pages.Retailers.metro());
        Shop.Catalog.Item.addToFavorites();

        checkAuthFrameOpen();

        User.Do.registration(Generate.phoneNumber(),true);
        User.Do.sendSms(Config.DEFAULT_SMS);

        checkIsUserAuthorized();
    }

    @CaseId(1272)
    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAuthAfterAddFavoriteOnItemCard() {
        kraken.get().page(Pages.Retailers.metro());
        AppManager.getWebDriver().get("https://stf-kraken.k-stage.sbermarket.tech/metro");
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        checkAuthFrameOpen();

        User.Do.registration("9999999999",true);
        User.Do.sendSms(Config.DEFAULT_SMS);

        checkIsUserAuthorized();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1492)
    @Test(  description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteProductToCart() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        Shop.AuthModal.hitSberIdButton();
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());

        Shop.Favorites.openFavorites();

        checkFavoriteIsNotEmpty();

        Shop.Favorites.Item.addToCart();
        Shop.Cart.open();

        checkCartIsOpen();
        checkCartNotEmpty();
        Shop.Cart.dropAll();
        checkCartEmpty();
    }

    @Skip
    @Issue("STF-8253")
    @CaseId(1494)
    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddFavoriteProductsFromCardToCart() {
        kraken.get().baseUrl();
        Shop.AuthModal.openAuthOnLanding();
        Shop.AuthModal.hitSberIdButton();
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());

        Shop.Favorites.openFavorites();
        checkFavoriteIsNotEmpty();

        Shop.Favorites.openFavoritesCard();
        Shop.Favorites.Item.addToCartByButton();
        Shop.Cart.open();

        checkCartIsOpen();
        checkCartNotEmpty();

        Shop.Cart.dropAll();
        checkCartEmpty();
    }
}
