package ru.instamart.reforged.stf.drawer.cart_new;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartNewCheck extends Check, CartNewElement {

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        waitAction().shouldBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверяем, что корзина открыта")
    default void checkCartClose() {
        waitAction().shouldNotBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        waitAction().shouldBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что корзина не пустая")
    default void checkCartNotEmpty() {
        waitAction().shouldNotBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что кнопка заказа доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(confirmOrderButton).isEnabled();
    }

    @Step("Проверяем, что кнопка заказа недоступна")
    default void checkOrderButtonIsNotEnabled() {
        Assert.assertFalse(waitAction().shouldBeVisible(confirmOrderButton).isEnabled());
    }

    @Step("Проверяем, что в шторке корзины есть кнопка закрытия")
    default void checkCartCloseButtonIsVisible() {
        waitAction().shouldBeVisible(closeButton);
    }

    @Step("Проверяем, что в шторке корзины есть кнопка закрытия")
    default void checkCartReturnToCatalogButtonIsVisible() {
        waitAction().shouldBeVisible(backToCatalogButton);
    }
}
