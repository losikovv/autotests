package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.UserData;


// Тесты любимых товаров


public class FavoriteProducts extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {"acceptance","regression"},
            priority = 501
    )
    public void noAccessToFavoritesForUnauthorizedUser(){
        assertPageIsUnavailable(Pages.Site.Profile.favorites());
    }


    @Test(
            description = "Переход в любимые товары по кнопке",
            groups = {"acceptance","regression"},
            priority = 502
    )
    public void successOpenFavorites() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.shopping().openFavorites();

        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Favorite.inStockFilterButton()),
                "Не работает переход в любимые товары по кнопке в шапке");
    }


    @Test(
            description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {"acceptance","regression"},
            priority = 503
    )
    public void noFavoriteProductsByDefault() throws Exception {
        kraken.perform().registration();

        kraken.get().favoritesPage();

        Assert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Дефолтный список любимых товаров у нового пользователя не пуст");

        kraken.cleanup().users();
    }


    @Test(
            description = "Добавление любимого товара из карточки товара и проверка списка",
            groups = {"acceptance","regression"},
            priority = 504
    )
    public void successAddFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        kraken.get().favoritesPage();

        softAssert.assertFalse(kraken.detect().isFavoritesEmpty(),
                "Не работает добавление любимого товара из карточки товара");

        softAssert.assertAll();
        kraken.cleanup().users();
    }


    @Test(
            description = "Удаление любимого товара из карточки товара и проверка списка",
            groups = {"acceptance","regression"},
            priority = 505
    )
    public void successDeleteFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        kraken.get().favoritesPage();

        softAssert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Не работает удаление любимого товара из карточки товара");

        softAssert.assertAll();
        kraken.cleanup().users();
    }


    @Test(
            description = "Удаление всех любимых товаров",
            groups = {"regression"},
            priority = 506
    )
    public void successCleanupFavorites() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();

        kraken.search().item("молоко");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.search().item("сыр");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.search().item("вода");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.search().item("бананы");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.search().item("яйца");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.search().item("хлеб");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.shopping().closeItemCard();

        kraken.get().favoritesPage();
        kraken.shopping().deleteFavorites();

        softAssert.assertTrue(kraken.detect().isFavoritesEmpty(),
                "Не работает удаление всех любимых товаров");

        softAssert.assertAll();
        kraken.cleanup().users();
    }


    @Test(
            description = "Проверка работоспособности фильтров Любимых товаров",
            groups = {"regression"},
            priority = 507
    )
    public void successFilterFavorites() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().loginAs("admin");
        kraken.get().favoritesPage();

        softAssert.assertTrue(kraken.detect().isFavoriteFilterAllItems(),
                "По умолчанию не выставлен фильтр Все товары");

        kraken.shopping().filterFavoritesInStock();

        softAssert.assertTrue(kraken.detect().isFavoriteFilterInStock(),
                "Не работает кнопка фильтра В наличии");

        kraken.shopping().filterFavoritesNotInStock();

        softAssert.assertTrue(kraken.detect().isFavoriteFilterNotInStock(),
                "Не работает кнопка фильтра Нет в наличии");

        kraken.shopping().filterFavoritesAllItems();

        softAssert.assertTrue(kraken.detect().isFavoriteFilterAllItems(),
                "Не работает кнопка фильтра Все товары");

        softAssert.assertAll();
    }


    @Test(
            description = "Проверка работоспособности подгрузки страниц в Любимых товарах",
            groups = {"regression"},
            priority = 508
    )
    public void successShowMoreLoad() throws Exception {
        kraken.perform().loginAs("admin");
        kraken.get().favoritesPage();

        kraken.jivosite().open();

        kraken.shopping().hitShowMoreFavorites();
        Assert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Favorite.secondPageProduct()),
                "Подгрузка страниц не рабоатет");
    }



    @Test(
            description = "Регистрация, при попытке добавить товар в любимые товары неавторизованным",
            groups = {"regression"},
            priority = 509
    )
    public void successRegAfterAddFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "Не открывается модалка регистрации после попытки добавления товара в любимые товары");

        kraken.perform().regSequence(kraken.generate().testUserData());
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает регистрация после попытки добавления товара в любимые товары");

        softAssert.assertAll();
        kraken.cleanup().users();
    }


    @Test(
            description = "Авторизация при попытке добавить товар в избранное неавторизованным",
            groups = {"regression"},
            priority = 510
    )
    public void successAuthAfterAddFavoriteOnItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        final UserData testuser = kraken.generate().testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.get().page(Pages.Site.Retailers.metro());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();

        softAssert.assertTrue(kraken.detect().isAuthModalOpen(),
                "Не открывается модалка авторизации после попытки добавления товара в избранное");

        kraken.perform().authSequence(testuser);
        kraken.perform().sendForm();

        softAssert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает авторизация после попытки добавления товара в избранное");

        softAssert.assertAll();
        kraken.cleanup().users();
    }


    @Test(
            description = "Тест добавления товаров в корзину из списка любимых товаров",
            groups = {"acceptance","regression"},
            priority = 511
    )
    public void successAddFavoriteProductsToCart() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().registration();
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не установлен\n");

        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitAddToFavoritesButton();
        kraken.get().favoritesPage();
      
        softAssert.assertFalse(kraken.detect().isFavoritesEmpty(),
                "Не работает добавление любимого товара из карточки товара");

        kraken.shopping().openFirstFavoriteItemCard();
        kraken.shopping().hitPlusButton();
        kraken.shopping().closeItemCard();
        kraken.perform().waitingFor(1); // ждем пока уберется алерт
        kraken.shopping().openCart();

        softAssert.assertTrue(kraken.detect().isCartOpen(),
                "Не открывается корзина из списка любимых товаров\n");

        softAssert.assertFalse(kraken.detect().isCartEmpty(),
                "Не работает добавление товаров в корзину из списка любимых товаров\n");

        softAssert.assertAll();
        kraken.cleanup().users();
    }
}
