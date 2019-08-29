package ru.instamart.tests.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.AppManager.session;

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

        Shop.Favorites.open();

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

        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Shop.Favorites.open();

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

        Shop.Catalog.Item.addToCart();

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

        Shop.Catalog.Item.addToFavorites();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

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
            Shop.Catalog.Item.addToFavorites();
            kraken.get().favoritesPage();
            Shop.Favorites.Item.removeFromFavorites();
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

        Shop.Search.item("молоко");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.item("сыр");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.item("вода");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.item("бананы");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.item("яйца");
        Shop.Catalog.Item.addToFavorites();

        Shop.Search.item("хлеб");
        Shop.Catalog.Item.addToFavorites();

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

        Shop.Favorites.applyFilterInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("inStock"),
                    "\nВ любимых товарах не применяется фильтр \"В наличии\"");

        Shop.Favorites.applyFilterNotInStock();

        softAssert.assertTrue(
                kraken.detect().isFavoritesFiltered("outOfStock"),
                    "\nВ любимых товарах не применяется фильтр \"Нет в наличии\"");

        Shop.Favorites.applyFilterAllItems();

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
            groups = {"regression"},
            priority = 411
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        Shop.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");

        User.Do.regSequence(generate.testCredentials("user"));
        Shop.AuthModal.submit();

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
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из карточки в любимые товары");

        User.Do.regSequence(generate.testCredentials("user"));
        Shop.AuthModal.submit();

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
        Shop.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из каталога в избранное");

        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(session.user.getEmail(), session.user.getPassword());
        Shop.AuthModal.submit();

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
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из карточки в избранное");

        Shop.AuthModal.switchToAuthorisationTab();
        Shop.AuthModal.fillAuthorisationForm(session.user.getEmail(), session.user.getPassword());
        Shop.AuthModal.submit();

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
        Shop.Cart.drop();
        kraken.get().favoritesPage();

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
            groups = {"acceptance","regression"},
            priority = 416
    )
    public void successAddFavoriteProductToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
        Shop.Cart.drop();
        kraken.get().favoritesPage();

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
