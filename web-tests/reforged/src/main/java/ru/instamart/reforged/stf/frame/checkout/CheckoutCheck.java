package ru.instamart.reforged.stf.frame.checkout;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CheckoutCheck extends Check, CheckoutElement {

    @Step("Проверяем видна ли кнопка оформления заказа из чекаута")
    default void checkCheckoutButtonIsVisible() {
        waitAction().shouldBeVisible(checkoutButton).isDisplayed();
    }
}
