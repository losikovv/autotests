package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        waitAction().shouldBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверяем, что корзина закрыта")
    default void checkCartClose() {
        waitAction().shouldNotBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        waitAction().shouldBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что корзина не пуста")
    default void checkCartNotEmpty() {
        waitAction().shouldNotBeVisible(cartIsEmptyPlaceholder);
    }
}
