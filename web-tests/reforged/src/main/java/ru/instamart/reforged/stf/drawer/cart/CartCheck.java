package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что кнопка заказа доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(submitOrder).isEnabled();
    }

    @Step("Проверяем, что спиннер пропал")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(costSpinner);
    }

    @Step("Проверяем, что корзина не пустая")
    default void checkCartNotEmpty() {
        Kraken.waitAction().shouldNotBeVisible(placeholder);
    }

    @Step("Проверяем, что корзина пустая")
    default void checkCartEmpty() {
        Kraken.waitAction().shouldBeVisible(placeholder);
    }
}
