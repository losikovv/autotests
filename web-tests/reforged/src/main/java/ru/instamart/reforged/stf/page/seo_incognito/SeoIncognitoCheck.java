package ru.instamart.reforged.stf.page.seo_incognito;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SeoIncognitoCheck extends Check, SeoIncognitoElement {

    @Step("Проверяем, что сетка товаров видна")
    default void checkProductGridVisible() {
        waitAction().shouldBeVisible(productsGrid);
    }

    @Step("Проверяем, что кнопка 'Добавить в корзину' видна")
    default void checkAddToCartButtonVisible() {
        waitAction().shouldBeVisible(addFirstProductToCart);
    }

}
