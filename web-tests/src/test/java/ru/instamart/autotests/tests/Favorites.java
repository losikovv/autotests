package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Favorites extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {"acceptance","regression"},
            priority = 501
    )
    public void noAccessToFavoritesForUnauthorizedUser(){
        assertPageIsUnavailable(Pages.Site.Profile.favorites());
    }

    @Test(  description = "Переход в любимые товары по кнопке",
            groups = {"smoke","acceptance","regression"},
            priority = 502
    )
    public void successOpenFavorites() {
        kraken.perform().loginAs(session.user);

        ShopHelper.Favorites.open();

        Assert.assertTrue(
                kraken.detect().isInFavorites(),
                    "Не работает переход в любимые товары по кнопке в шапке\n");
    }

    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void noFavoriteItemsByDefault() {
        kraken.perform().registration();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Дефолтный список любимых товаров у нового пользователя не пуст\n");
    }

    @Test(  description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {"regression"},
            priority = 504
    )
    public void successAddFavoriteOnItemCard() {
        kraken.perform().loginAs(session.user);
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
            priority = 505
    )
    public void successAddFavoriteFromCatalog() {
        kraken.perform().loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        ShopHelper.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {"regression"},
            priority = 506
    )
    public void successDeleteFavoriteOnItemCard() {
        kraken.perform().loginAs(session.user);
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
            priority = 507
    )
    public void successDeleteFavoriteOnList() {
        kraken.perform().loginAs(session.user);
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
            priority = 508
    )
    public void successCleanupFavorites() {
        kraken.perform().loginAs(session.user);

        kraken.search().item("молоко");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.search().item("сыр");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.search().item("вода");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.search().item("бананы");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.search().item("яйца");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.search().item("хлеб");
        ShopHelper.Catalog.Item.addToFavorites();

        kraken.drop().favorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не очищается список любимых товаров\n");
    }

    @Test(  description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {"acceptance","regression"},
            priority = 509
    )
    public void successApplyFilters() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
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
            priority = 510
    )
    public void successShowMoreLoad() throws AssertionError {
        kraken.perform().loginAs(session.admin);
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
            priority = 511
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");

        kraken.perform().regSequence(generate.testCredentials("user"));
        kraken.perform().sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из каталога в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Регистрация, при попытке добавить товар из карточки товара в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 512
    )
    public void successRegAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка регистрации после попытки добавления товара из карточки в любимые товары");

        kraken.perform().regSequence(generate.testCredentials("user"));
        kraken.perform().sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из карточки в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из каталога в избранное неавторизованным",
            groups = {"regression"},
            priority = 513
    )
    public void successAuthAfterAddFavoriteOnCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из каталога в избранное");

        kraken.perform().authSequence(session.user);
        kraken.perform().sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из каталога в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {"regression"},
            priority = 514
    )
    public void successAuthAfterAddFavoriteOnItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        ShopHelper.Catalog.Item.open();
        ShopHelper.ItemCard.addToFavorites();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается модалка авторизации после попытки добавления товара из карточки в избранное");

        kraken.perform().authSequence(session.user);
        kraken.perform().sendForm();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из карточки в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            groups = {"regression"},
            priority = 515
    )
    public void successAddFavoriteProductsFromCardToCart() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
        kraken.drop().cart();
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
            priority = 516
    )
    public void successAddFavoriteProductToCart() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
        kraken.drop().cart();
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
