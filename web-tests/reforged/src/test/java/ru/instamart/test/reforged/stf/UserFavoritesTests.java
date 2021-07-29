package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.userFavorites;

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
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().phoneRegistration();
        userFavorites().goToPage();
        userFavorites().checkPageUrl(userFavorites().pageUrl());
    }

    @CaseId(1265)
    @Test(  description = "Проверка пустого списка любимых товаров для нового пользователя",
            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"}
    )
    public void noFavoriteItemsByDefault() {

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

    }

    @CaseId(1271)
    @Test(  description = "Регистрация, при попытке добавить товар из каталога в любимые товары неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successRegAfterAddFavoriteOnCatalog() {

    }

    @CaseId(1272)
    @Test(  description = "Авторизация, при попытке добавить товар из карточки товара в избранное неавторизованным",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"}
    )
    public void successAuthAfterAddFavoriteOnItemCard() {

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

    }
}
