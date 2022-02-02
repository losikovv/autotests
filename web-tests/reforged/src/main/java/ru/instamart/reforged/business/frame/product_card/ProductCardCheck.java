package ru.instamart.reforged.business.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ProductCardCheck extends Check, ProductCardElement {

    @Step("Проверяем, что карточка продукта открыта")
    default void checkProductCardVisible() {
        waitAction().shouldBeVisible(itemName);
    }

    @Step("Проверяем, что карточка продукта закрыта")
    default void checkProductCardIsNotVisible() {
        waitAction().shouldNotBeVisible(itemName);
    }

    @Step("Проверяем, что кнопка 'уменьшить количество продуктов в корзине' не отображается")
    default void checkDecreaseCountButtonNotVisible() {
        waitAction().shouldNotBeVisible(decrease);
    }
}
