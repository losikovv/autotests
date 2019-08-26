package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.appmanager.User;
import ru.instamart.autotests.testdata.generate;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class UserFavoritesTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Do.quickLogout();
    }

    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void noAccessToFavoritesForUnauthorizedUser(){
        assertPageIsUnavailable(Pages.Site.Profile.favorites());
    }

    @Test(  description = "Переход в любимые товары по кнопке",
            groups = {"smoke","acceptance","regression"},
            priority = 402
    )
    public void successOpenFavorites() {
        User.Do.loginAs(session.user);

        ShopHelper.Favorites.open();

        assertPageIsAvailable();

        Assert.assertTrue(
                kraken.detect().isInFavorites(),
                    "Не работает переход в любимые товары по кнопке в шапке\n");
    }

    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {"acceptance","regression"},
            priority = 403
    )
    public void noFavoriteItemsByDefault() {
        User.Do.registration();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Дефолтный список любимых товаров у нового пользователя не пуст\n");
    }

    @Test(  description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {"regression"},
            priority = 404
    )
    public void successAddFavoriteOnItemCard() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        ShopHelper.Favorites.open();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Тест успешного добавления любомого товара из сниппета в каталоге",
            groups = {"regression"},
            priority = 405
    )
    public void successAddFavoriteFromCatalog() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        ShopHelper.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {"regression"},
            priority = 406
    )
    public void successDeleteFavoriteOnItemCard() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        ShopHelper.Catalog.Item.addToFavorites();
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает удаление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из списка",
            groups = {"regression"},
            priority = 407
    )
    public void successDeleteFavoriteOnList() {
        User.Do.loginAs(session.user);
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.drop().favorites();
        } else {
            kraken.get().page(Pages.Site.Retailers.metro());
            ShopHelper.Catalog.Item.addToFavorites();
            kraken.get().favoritesPage();
            ShopHelper.Favorites.Item.removeFromFavorites();
        }

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает удаление любимого товара из списка любимых товаров\n");
    }

    @Test(  description = "Удаление всех любимых товаров",
            groups = {"regression"},
            priority = 408
    )
    public void successCleanupFavorites() {
        User.Do.loginAs(session.user);

        ShopHelper.Search.item("молоко");
        ShopHelper.Catalog.Item.addToFavorites();

        ShopHelper.Search.item("сыр");
        ShopHelper.Catalog.Item.addToFavorites();

        ShopHelper.Search.item("вода");
        ShopHelper.Catalog.Item.addToFavorites();

        ShopHelper.Search.item("бананы");
        ShopHelper.Catalog.Item.addToFavorites();

        ShopHelper.Search.item("яйца");
        ShopHelper.Catalog.Item.addToFavorites();

        ShopHelper.Search.item("хлеб");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.drop().favorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не очищается список любимых товаров\n");
    }

    @Test(  description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {"acceptance","regression"},
            priority = 409
    )
    public void successApplyFilters() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
        kraken.get().favoritesPage();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("all"),
                    "\nВ любимых товарах по умолчанию не применен фильтр \"Все товары\"");

        ShopHelper.Favorites.applyFilterInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("inStock"),
                    "\nВ любимых товарах не применяется фильтр \"В наличии\"");

        ShopHelper.Favorites.applyFilterNotInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("outOfStock"),
                    "\nВ любимых товарах не применяется фильтр \"Нет в наличии\"");

        ShopHelper.Favorites.applyFilterAllItems();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("all"),
                    "\nВ любимых товарах не применяется фильтр \"Все товары\"");

        softAssert.assertAll();
    }

    @Test(  description = "Проверка работоспособности подгрузки страниц в Любимых товарах",
            groups = {"regression"},
            priority = 410
    )
    public void successShowMoreLoad() throws AssertionError {
        User.Do.loginAs(session.admin);
        kraken.get().favoritesPage();
        ShopHelper.Jivosite.open();

        if(kraken.detect().isElementPresent(Elements.Favorites.showMoreButton())) {
            ShopHelper.Favorites.showMore();
        } else {
            throw new AssertionError(
                    "Не выполнены предусловия теста, нет кнопки подгрузки товаров\n");
        }

        while (kraken.detect().isElementPresent(Elements.Favorites.showMoreButton())) {
            ShopHelper.Favorites.showMore();
        }

        Assert.assertTrue(
                kraken.detect().isElementPresent(Elements.Favorites.Product.snippet()),
                    "Не рабоатет подгрузка страниц списка любимых товаров\n");
    }

    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 411
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");

        User.Do.regSequence(generate.testCredentials("user"));
        User.Do.sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из каталога в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Регистрация, при попытке добавить товар из карточки товара в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 412
    )
    public void successRegAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из карточки в любимые товары");

        User.Do.regSequence(generate.testCredentials("user"));
        User.Do.sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из карточки в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из каталога в избранное неавторизованным",
            groups = {"regression"},
            priority = 413
    )
    public void successAuthAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из каталога в избранное");

        User.Do.authSequence(session.user);
        User.Do.sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из каталога в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {"regression"},
            priority = 414
    )
    public void successAuthAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из карточки в избранное");

        User.Do.authSequence(session.user);
        User.Do.sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из карточки в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            groups = {"regression"},
            priority = 415
    )
    public void successAddFavoriteProductsFromCardToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
        ShopHelper.Cart.drop();
        kraken.get().favoritesPage();

        ShopHelper.Catalog.Item.addToCart();
        kraken.await().implicitly(1); // ждем пока уберется алерт
        ShopHelper.Cart.open();

        softAssert.assertTrue(
                kraken.detect().isCartOpen(),
                    "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nНе работает добавление товаров в корзину из карточки товара, открытой из списка любимых товаров\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {"acceptance","regression"},
            priority = 416
    )
    public void successAddFavoriteProductToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
        ShopHelper.Cart.drop();
        kraken.get().favoritesPage();

        ShopHelper.Favorites.Item.addToCart();
        ShopHelper.Cart.open();

        softAssert.assertTrue(
                kraken.detect().isCartOpen(),
                    "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nНе работает добавление товаров в корзину из списка любимых товаров\n");

        softAssert.assertAll();
    }
}
