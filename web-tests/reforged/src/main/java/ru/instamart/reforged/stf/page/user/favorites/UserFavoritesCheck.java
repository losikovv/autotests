package ru.instamart.reforged.stf.page.user.favorites;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public interface UserFavoritesCheck extends Check, UseFavoritesElement {

    @Step("Проверяем что список избранного пуст")
    default void checkEmptyFavorites() {
        Kraken.waitAction().shouldBeVisible(emptyFavorites);
    }

    @Step("Проверяем что список избранного не пустой")
    default void checkNotEmptyFavorites() {
        Kraken.waitAction().shouldNotBeVisible(emptyFavorites);
    }

    @Step("Проверяем что активен фильтр Все товары")
    default void checkAllGoodsActive() {
        assertEquals(activeElementFilter.getText(), allGoods.getText(), "Фильтр Все товары не активен");
    }

    @Step("Проверяем что активен фильтр В наличии")
    default void checkInStockActive() {
        assertEquals(activeElementFilter.getText(), inStock.getText(), "Фильтр В наличии не активен");
    }

    @Step("Проверяем что активен фильтр Нет в наличии")
    default void checkOutOfStockActive() {
        assertEquals(activeElementFilter.getText(), outOfStock.getText(), "Фильтр Нет в наличии не активен");
    }

    @Step("Проверяем что не отображается кнопка Показать еще")
    default void checkShowMoreNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(showMore);
    }

    @Step("Проверяем что подгрузились избранные товары")
    default void checkCountChange(final int initCount, final int finalCount) {
        assertTrue(initCount < finalCount, "Товары не подгрузились");
    }
}
