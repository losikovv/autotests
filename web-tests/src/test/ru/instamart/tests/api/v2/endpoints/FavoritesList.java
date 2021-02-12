package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.condition.FavoritesCondition;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Избранное")
public class FavoritesList extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Создание сессии")
    public void preconditions() {
        FavoritesCondition.makeSession();
    }

    @Test(  description = "Получаем пустой список любимых товаров",
            groups = {"api-v2-smoke"}
            )
    public void testEmptyFavoritesList() {
        FavoritesCondition
                .newTest()
                .getFavoritesItems(1)
                .emptyFavoritesList();
    }

    @Test(  description = "Добавление товара в избранное",
            groups = {"api-v2-smoke"},
            enabled = false
    )
    public void testAddItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(1060837)
                .itemWasAdded();
    }

    @Test(  description = "Добавление товара в избранное с несущетвующим id",
            groups = {"api-v2-smoke"}
    )
    public void testNegativeAddItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(1)
                .itemWasNotAdded();
    }

    @Test(  description = "Удаление товара из избранного по sku",
            groups = {"api-v2-smoke"},
            enabled = false
    )
    public void testDeleteItemToFavoritesList() {
        FavoritesCondition
                .newTest()
                .addToFavorites(15886)
                .itemWasAdded()
                .removeFromFavorites(15886)
                .itemWasRemoved();
    }

    @Test(  description = "Получаем пустой список sku любимых товаров",
            groups = {"api-v2-smoke"}
    )
    public void testEmptySkuFavoritesList() {
        FavoritesCondition
                .newTest()
                .getAllSkuItemsFromFavorites()
                .emptySkuFavoritesList();
    }

    @Test(  description = "Добавление товара в избранное по его Sku",
            groups = {"api-v2-smoke"}
    )
    public void testAddItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(15886)
                .itemWasAddedBySku();
    }

    @Test(  description = "Добавление товара в избранное с несущетвующим Sku",
            groups = {"api-v2-smoke"}
    )
    public void testNegativeAddItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(1)
                .itemWasNotAddedBySku();
    }

    @Test(  description = "Удаление товара из избранного по sku",
            groups = {"api-v2-smoke"}
    )
    public void testDeleteItemToFavoritesListBySku() {
        FavoritesCondition
                .newTest()
                .addToFavoritesBySku(15886)
                .itemWasAddedBySku()
                .removeFromFavoritesBySku(15886)
                .itemWasRemovedBySku();
    }
}
