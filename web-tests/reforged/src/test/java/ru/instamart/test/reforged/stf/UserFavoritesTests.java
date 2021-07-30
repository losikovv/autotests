package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Любимые товары")
public final class UserFavoritesTests extends BaseTest {

    @CaseId(1263)
    @Test(  description = "Тест недоступности страницы любимых товаров неавторизованному юзеру",
            groups = {
                    "sbermarket-Ui-smoke",
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noAccessToFavoritesForUnauthorizedUser() {
        userFavorites().goToPage();
        userFavorites().checkForbiddenPageUrl(userFavorites().pageUrl());
    }

    @CaseId(1265)
    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noFavoriteItemsByDefault() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().createAccount();
        shop().interactHeader().checkProfileButtonVisible();
        userFavorites().goToPage();
        userFavorites().checkEmptyFavorites();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().createAccount();
        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().createAccount();

        //TODO: Нужен апи метод для добавления избранных
        shop().addFirstItemToFavorite();
        userFavorites().goToPage();
        userFavorites().removeFirstFavoriteItem();
        userFavorites().refresh();
        userFavorites().checkEmptyFavorites();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().createAccount();

        //TODO: Нужен апи метод для добавления избранных
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMS();
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();
        userFavorites().checkAllGoodsActive();
        userFavorites().filterInStock();
        userFavorites().checkInStockActive();
        userFavorites().filterOutOfStock();
        userFavorites().checkOutOfStockActive();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMS();
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().addCookie(StfPage.cookieAlert);
        userFavorites().goToPage();
        userFavorites().checkNotEmptyFavorites();

        final int initCount = userFavorites().getFavoritesCount();
        userFavorites().showMore();
        userFavorites().checkShowMoreNotVisible();
        userFavorites().checkCountChange(initCount, userFavorites().getFavoritesCount());
    }

    @CaseId(1271)
    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successRegAfterAddFavoriteOnCatalog() {
        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().addFirstItemToFavorite();
        shop().interactAuthModal().createAccount();
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1272)
    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAuthAfterAddFavoriteOnItemCard() {
        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().openFirstProductCard();
        shop().interactProductCard().addToFavorite();
        shop().interactAuthModal().createAccount();
        shop().interactHeader().checkProfileButtonVisible();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMS();
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().addToCartFirstFavoriteItem();
        userFavorites().interactHeader().clickToCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
        //TODO: Очистку реализовать через апи, когда появится метод
        userFavorites().interactHeader().interactCart().clearCart();
        userFavorites().interactHeader().interactCart().confirmClearCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMS();
        shop().interactHeader().checkProfileButtonVisible();

        userFavorites().goToPage();
        userFavorites().openCartForFirstFavoriteItem();
        userFavorites().interactProductCart().clickOnBuy();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
        //TODO: Очистку реализовать через апи, когда появится метод
        userFavorites().interactHeader().interactCart().clearCart();
        userFavorites().interactHeader().interactCart().confirmClearCart();
        userFavorites().interactHeader().interactCart().checkCartNotEmpty();
    }
}
