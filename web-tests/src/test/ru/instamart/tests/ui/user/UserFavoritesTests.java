package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.FavoriteItemsCheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Flaky;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class UserFavoritesTests extends TestBase {
    public static String modalType;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    FavoriteItemsCheckpoints favoriteChecks = new FavoriteItemsCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAccessToFavoritesForUnauthorizedUser(){
        baseChecks.checkPageIsUnavailable(Pages.UserProfile.favorites());
    }
    @Issue(value="STF-6773")
    @CaseId(1264)
    @Test(  description = "Переход в любимые товары по кнопке, новый пользователь",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-Ui-smoke","testing"}
    )
    @Flaky
    public void successOpenFavorites() {
        String phone = Generate.phoneNumber();
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        Shop.Favorites.open();
        baseChecks.checkPageIsAvailable();
        favoriteChecks.checkIsFavoriteOpen();
    }

    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noFavoriteItemsByDefault() {
        User.Do.registration();
        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Дефолтный список любимых товаров у нового пользователя не пуст\n");
    }

    @Test(  description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteOnItemCard() {
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.reach().cleanFavorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Shop.Favorites.open();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Тест успешного добавления любомого товара из сниппета в каталоге",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteFromCatalog() {
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.reach().cleanFavorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successDeleteFavoriteOnItemCard() {
        User.Do.loginAs(UserManager.getDefaultUser());
        kraken.reach().cleanFavorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.addToFavorites();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает удаление любимого товара из карточки товара\n");
    }

    //TODO возможно дублирует тест 408
    @Test(  description = "Удаление любимого товара из списка",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successDeleteFavoriteOnList() {
        User.Do.loginAs(UserManager.getDefaultUser());
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.reach().cleanFavorites();
        } else {
            kraken.get().page(Pages.Retailers.metro());
            Shop.Catalog.Item.addToFavorites();
            kraken.get().userFavoritesPage();
            Shop.Favorites.Item.removeFromFavorites();
        }

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает удаление любимого товара из списка любимых товаров\n");
    }

    @Test(  description = "Удаление всех любимых товаров",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    ) public void successCleanupFavorites() {
        User.Do.loginAs(UserManager.getDefaultUser());

        Shop.Search.searchItem("молоко");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.searchItem("сыр");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.searchItem("вода");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.searchItem("бананы");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.searchItem("яйца");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.searchItem("хлеб");
        Shop.Catalog.Item.addToFavorites();

        kraken.reach().cleanFavorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не очищается список любимых товаров\n");
    }

    @Test(  description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successApplyFilters() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().userFavoritesPage();

        Assert.assertTrue(
                !kraken.detect().isFavoritesEmpty(),
                    failMessage("Не выполнено предусловие, у пользователя нет любимых товаров"));

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("all"),
                    failMessage("\nВ любимых товарах по умолчанию не применен фильтр \"Все товары\""));

        Shop.Favorites.applyFilterInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("inStock"),
                    failMessage("\nВ любимых товарах не применяется фильтр \"В наличии\""));

        Shop.Favorites.applyFilterNotInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("outOfStock"),
                    failMessage("\nВ любимых товарах не применяется фильтр \"Нет в наличии\""));

        Shop.Favorites.applyFilterAllItems();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("all"),
                    failMessage("\nВ любимых товарах не применяется фильтр \"Все товары\""));

        softAssert.assertAll();
    }

    @Test(  description = "Проверка работоспособности подгрузки страниц в Любимых товарах",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successShowMoreLoad() throws AssertionError {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.get().userFavoritesPage();
        Shop.Jivosite.open();

        if(kraken.detect().isElementPresent(Elements.Favorites.showMoreButton())) {
            Shop.Favorites.showMore();
        } else {
            throw new AssertionError(
                    "Не выполнены предусловия теста, нет кнопки подгрузки товаров\n");
        }

        while (kraken.detect().isElementPresent(Elements.Favorites.showMoreButton())) {
            Shop.Favorites.showMore();
        }

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Favorites.Product.snippet()),
                    "Не рабоатет подгрузка страниц списка любимых товаров\n");
    }

    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Retailers.metro());
        Shop.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");

        User.Do.regSequence(UserManager.getUser());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из каталога в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Регистрация, при попытке добавить товар из карточки товара в любимые товары неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successRegAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Retailers.metro());
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из карточки в любимые товары");

        User.Do.regSequence(UserManager.getUser());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из карточки в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из каталога в избранное неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAuthAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Retailers.metro());
        Shop.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из каталога в избранное");

        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из каталога в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAuthAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Retailers.metro());
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из карточки в избранное");

        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из карточки в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteProductsFromCardToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(UserManager.getDefaultAdmin());
        Shop.Cart.drop();
        kraken.get().userFavoritesPage();

        Shop.Catalog.Item.addToCart();
        kraken.await().implicitly(1); // ждем пока уберется алерт
        Shop.Cart.open();

        softAssert.assertTrue(
                kraken.detect().isCartOpen(),
                    "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nНе работает добавление товаров в корзину из карточки товара, открытой из списка любимых товаров\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddFavoriteProductToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(UserManager.getDefaultAdmin());
        Shop.Cart.drop();
        kraken.get().userFavoritesPage();

        Shop.Favorites.Item.addToCart();
        Shop.Cart.open();

        softAssert.assertTrue(
                kraken.detect().isCartOpen(),
                    "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nНе работает добавление товаров в корзину из списка любимых товаров\n");

        softAssert.assertAll();
    }
}
