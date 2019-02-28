package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;


// Тесты любимых товаров


public class FavoriteProducts extends TestBase {


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
            groups = {"acceptance","regression"},
            priority = 502
    )
    public void successOpenFavorites() throws Exception {
        kraken.perform().loginAs(session.admin);
        kraken.shopping().openFavorites();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Favorites.inStockFilterButton()),
                "Не работает переход в любимые товары по кнопке в шапке\n");
    }


    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void noFavoriteProductsByDefault() throws Exception {
        kraken.perform().registration();

        Assert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Дефолтный список любимых товаров у нового пользователя не пуст\n");
    }


    @Test(  description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {"acceptance","regression"},
            priority = 504
    )
    public void successAddFavoriteOnItemCard() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        Assert.assertFalse(kraken.detect().isFavoritesEmpty(),
                "Не работает добавление любимого товара из карточки товара\n");
    }


    @Test(  description = "Тест успешного добавления любомого товара из сниппета в каталоге",
            groups = {"regression"},
            priority = 505
    )
    public void successAddFavoriteFromCatalog() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        kraken.shopping().hitFirstItemAddToFavoritesButton();

        Assert.assertFalse(kraken.detect().isFavoritesEmpty(),
                "Не работает добавление любимого товара из карточки товара\n");
    }


    @Test(  description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {"acceptance","regression"},
            priority = 506
    )
    public void successDeleteFavoriteOnItemCard() throws Exception {
        kraken.perform().loginAs(session.user);
        kraken.drop().favorites();
        kraken.get().page(Pages.Site.Retailers.metro());

        kraken.shopping().hitFirstItemAddToFavoritesButton();
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        Assert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Не работает удаление любимого товара из карточки товара\n");
    }


    @Test(  description = "Удаление любимого товара из списка",
            groups = {"acceptance","regression"},
            priority = 507
    )
    public void successDeleteFavoriteOnList() throws Exception {
        kraken.perform().loginAs(session.user);
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.drop().favorites();
        } else {
            kraken.get().page(Pages.Site.Retailers.metro());
            kraken.shopping().hitFirstItemAddToFavoritesButton();
            kraken.get().favoritesPage();
            kraken.shopping().hitFirstItemDeleteFromFavoritesButton();
        }

        Assert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Не работает удаление любимого товара из списка любимых товаров\n");
    }


    @Test(  description = "Удаление всех любимых товаров",
            groups = {"regression"},
            priority = 508
    )
    public void successCleanupFavorites() throws Exception {
        kraken.perform().loginAs(session.user);

        kraken.search().item("молоко");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.search().item("сыр");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.search().item("вода");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.search().item("бананы");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.search().item("яйца");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.search().item("хлеб");
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        kraken.drop().favorites();

        Assert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Не очищается список любимых товаров\n");
    }


    @Test(  description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {"regression"},
            priority = 509
    )
    public void successFilterFavorites() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
        kraken.get().favoritesPage();

        softAssert.assertTrue(kraken.detect().isFavoritesFiltered("all"),
                "\nВ любимых товарах по умолчанию не выставлен фильтр \"Все товары\"");

        kraken.shopping().filterFavoritesInStock();

        softAssert.assertTrue(kraken.detect().isFavoritesFiltered("inStock"),
                "\nВ любимых товарах не работает фильтр \"В наличии\"");

        kraken.shopping().filterFavoritesNotInStock();

        softAssert.assertTrue(kraken.detect().isFavoritesFiltered("outOfStock"),
                "\nВ любимых товарах не работает кнопка фильтра \"Нет в наличии\"");

        kraken.shopping().filterFavoritesAllItems();

        softAssert.assertTrue(kraken.detect().isFavoritesFiltered("all"),
                "\nВ любимых товарах не работает кнопка фильтра \"Все товары\"");

        softAssert.assertAll();
    }


    @Test(  description = "Проверка работоспособности подгрузки страниц в Любимых товарах",
            groups = {"regression"},
            priority = 510
    )
    public void successShowMoreLoad() throws Exception {
        kraken.perform().loginAs(session.admin);
        kraken.get().favoritesPage();

        kraken.jivosite().open();

        kraken.shopping().hitShowMoreFavorites();
        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Favorites.secondPageProduct()),
                "Не рабоатет подгрузка страниц списка любимых товаров\n");
    }



    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 511
    )
    public void successRegAfterAddFavoriteOnCatalog() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается модалка регистрации после попытки добавления товара из каталога в любимые товары");

        kraken.perform().regSequence(generate.testCredentials("user"));
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает регистрация после попытки добавления товара из каталога в любимые товары");

        softAssert.assertAll();
    }


    @Test(  description = "Регистрация, при попытке добавить товар из карточки товара в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 512
    )
    public void successRegAfterAddFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается модалка регистрации после попытки добавления товара из карточки в любимые товары");

        kraken.perform().regSequence(generate.testCredentials("user"));
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает регистрация после попытки добавления товара из карточки в любимые товары");

        softAssert.assertAll();
    }


    @Test(  description = "Авторизация, при попытке добавить товар из каталога в избранное неавторизованным",
            groups = {"regression"},
            priority = 513
    )
    public void successAuthAfterAddFavoriteOnCatalog() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().hitFirstItemAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается модалка авторизации после попытки добавления товара из каталога в избранное");

        kraken.perform().authSequence(session.user);
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация после попытки добавления товара из каталога в избранное");

        softAssert.assertAll();
    }


    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {"regression"},
            priority = 514
    )
    public void successAuthAfterAddFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "\nНе открывается модалка авторизации после попытки добавления товара из карточки в избранное");

        kraken.perform().authSequence(session.user);
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "\nНе работает авторизация после попытки добавления товара из карточки в избранное");

        softAssert.assertAll();
    }


    @Test(  description = "Тест добавления товаров в корзину из карточки товара, открытой из списка любимых товаров",
            groups = {"regression"},
            priority = 515
    )
    public void successAddFavoriteProductsFromCardToCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
        kraken.drop().cart();
        kraken.get().favoritesPage();

        kraken.shopping().hitFirstItemPlusButton();
        kraken.perform().waitingFor(1); // ждем пока уберется алерт
        kraken.shopping().openCart();

        softAssert.assertTrue(kraken.detect().isCartOpen(),
                "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "\nНе работает добавление товаров в корзину из карточки товара, открытой из списка любимых товаров\n");

        softAssert.assertAll();
    }


    @Test(  description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {"acceptance","regression"},
            priority = 516
    )
    public void successAddFavoriteProductsFromListToCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs(session.admin);
        kraken.drop().cart();
        kraken.get().favoritesPage();

        kraken.shopping().hitFirstItemPlusButton();
        kraken.perform().waitingFor(1); // ждем пока уберется алерт
        kraken.shopping().openCart();

        softAssert.assertTrue(kraken.detect().isCartOpen(),
                "\nНе открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "\nНе работает добавление товаров в корзину из списка любимых товаров\n");

        softAssert.assertAll();
    }
}
