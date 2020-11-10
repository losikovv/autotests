package ru.instamart.tests.user;

import instamart.core.testdata.ui.generate;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class UserFavoritesTests extends TestBase {

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            priority = 401,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAccessToFavoritesForUnauthorizedUser(){
        assertPageIsUnavailable(Pages.UserProfile.favorites());
    }

    @Test(  description = "Переход в любимые товары по кнопке",
            priority = 402,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-smoke","sbermarket-acceptance","sbermarket-regression"}
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
            priority = 403,
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
            priority = 404,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteOnItemCard() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Shop.Favorites.open();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Тест успешного добавления любомого товара из сниппета в каталоге",
            priority = 405,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteFromCatalog() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.addToCart();

        Assert.assertFalse(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает добавление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            priority = 406,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successDeleteFavoriteOnItemCard() {
        User.Do.loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Retailers.metro());

        Shop.Catalog.Item.addToFavorites();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToFavorites();

        Assert.assertTrue(
                kraken.detect().isFavoritesEmpty(),
                    "Не работает удаление любимого товара из карточки товара\n");
    }

    @Test(  description = "Удаление любимого товара из списка",
            priority = 407,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successDeleteFavoriteOnList() {
        User.Do.loginAs(session.user);
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.drop().favorites();
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
            priority = 408,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            }
    ) public void successCleanupFavorites() {
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
            priority = 409,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successApplyFilters() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
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
            priority = 410,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successShowMoreLoad() throws AssertionError {
        User.Do.loginAs(session.admin);
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
            priority = 411,
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

        User.Do.regSequence(generate.testCredentials("user"));
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из каталога в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Регистрация, при попытке добавить товар из карточки товара в любимые товары неавторизованным",
            priority = 412,
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

        User.Do.regSequence(generate.testCredentials("user"));
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация после попытки добавления товара из карточки в любимые товары");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из каталога в избранное неавторизованным",
            priority = 413,
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
        Shop.AuthModal.fillAuthorisationForm(session.user.getLogin(), session.user.getPassword());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из каталога в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            priority = 414,
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
        Shop.AuthModal.fillAuthorisationForm(session.user.getLogin(), session.user.getPassword());
        Shop.AuthModal.submit();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает авторизация после попытки добавления товара из карточки в избранное");

        softAssert.assertAll();
    }

    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            priority = 415,
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAddFavoriteProductsFromCardToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
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
            priority = 416,
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddFavoriteProductToCart() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.loginAs(session.admin);
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
