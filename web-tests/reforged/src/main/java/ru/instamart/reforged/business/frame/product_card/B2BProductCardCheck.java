package ru.instamart.reforged.business.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface B2BProductCardCheck extends Check, B2BProductCardElement {

    @Step("Проверяем, что карточка продукта открыта")
    default void checkProductCardVisible() {
        itemName.should().animationFinished();
        itemName.shouldBe().visible();
    }

    @Step("Проверяем, что карточка продукта закрыта")
    default void checkProductCardIsNotVisible() {
        itemName.should().invisible();
    }

    @Step("Проверяем, что кнопка 'уменьшить количество продуктов в корзине' не отображается")
    default void checkDecreaseCountButtonNotVisible() {
        decrease.should().invisible();
    }
}
