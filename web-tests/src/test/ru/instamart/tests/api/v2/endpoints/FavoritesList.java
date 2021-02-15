package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.condition.FavoritesCondition;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic(value = "Api")
@Feature(value = "Избранное")
public class FavoritesList extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        FavoritesCondition.makeSession();
    }

    @CaseId(13)
    @Test(groups = {"api-v2-regress"})
    @Story("Получаем пустой список любимых товаров")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyFavoritesList() {
        FavoritesCondition
                .newTest()
                .getFavoritesItems(1)
                .emptyFavoritesList();
    }

    @CaseId(128)
    @Test(groups = {"api-v2-smoke"})
    @Story("Добавление товара в избранное")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(1060837)
                .itemWasAdded();
    }

    @CaseId(129)
    @Test(groups = {"api-v2-regress"})
    @Story("Добавление товара в избранное с несуществующим id")
    @Severity(SeverityLevel.NORMAL)
    public void testNegativeAddItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(1)
                .itemWasNotAdded();
    }

    @CaseId(130)
    @Test(groups = {"api-v2-smoke"})
    @Story("Удаление товара из избранного")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(15886)
                .itemWasAdded()
                .removeFromFavorites(15886)
                .itemWasRemoved();
    }

    @CaseId(131)
    @Test(groups = {"api-v2-regress"})
    @Story("Получаем пустой список sku любимых товаров")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptySkuFavoritesList() {
        FavoritesCondition
                .newTest()
                .getAllSkuItemsFromFavorites()
                .emptySkuFavoritesList();
    }

    @CaseId(132)
    @Test(groups = {"api-v2-smoke"})
    @Story("Добавление товара в избранное по его Sku")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(15886)
                .itemWasAddedBySku();
    }

    @CaseId(133)
    @Test(groups = {"api-v2-regress"})
    @Story("Добавление товара в избранное с несуществующим Sku")
    @Severity(SeverityLevel.NORMAL)
    public void testNegativeAddItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(1)
                .itemWasNotAddedBySku();
    }

    @CaseId(134)
    @Test(groups = {"api-v2-regress"})
    @Story("Удаление товара из избранного по sku")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(15886)
                .itemWasAddedBySku()
                .removeFromFavoritesBySku(15886)
                .itemWasRemovedBySku();
    }
}