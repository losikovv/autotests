package ru.instamart.reforged.stf.page.user.favorites;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserFavoritesCheck extends Check, UseFavoritesElement {

    @Step("Проверяем что список избранного пуст")
    default void checkEmptyFavorites() {
        waitAction().shouldBeVisible(emptyFavorites);
    }

    @Step("Проверяем что список избранного не пустой")
    default void checkNotEmptyFavorites() {
        emptyFavorites.should().invisible();
    }

    @Step("Проверяем что подгрузились избранные товары")
    default void checkCountLess(final int initCount, final int finalCount) {
        assertTrue(initCount < finalCount, "Товары не подгрузились");
    }

    @Step("Проверяем, не отображается спиннер")
    default void checkSpinnerNotVisible() {
        spinner.should().invisible();
    }

    @Step("Проверяем что товаров стало {0}")
    default void checkCountChange(final int initCount, final int finalCount) {
        assertEquals(finalCount, initCount, "Количество товаров некорректно");
    }
}
