package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SearchCheck extends Check, SearchElement {

    @Step("Проверяем, что отображается кнопка добавления в корзину у первого товара в поиске")
    default void checkAddToCartButtonVisible() {
        waitAction().shouldBeVisible(firstAddToCartButton);
    }

    @Step("Проверяем, что сетка найденных товаров не отображается")
    default void checkSearchProductGridNotVisible() {
        waitAction().shouldNotBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что сетка найденных товаров отображается")
    default void checkSearchProductGridVisible() {
        waitAction().shouldBeVisible(searchProductGrid);
    }
}
