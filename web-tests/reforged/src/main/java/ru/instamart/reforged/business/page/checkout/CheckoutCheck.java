package ru.instamart.reforged.business.page.checkout;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CheckoutCheck extends Check, CheckoutElement {

    @Step("Проверяем, что кнопка 'Оформить заказ' в чекауте видна")
    default void checkSubmitFromCheckoutVisible() {
        waitAction().shouldBeVisible(submitCheckoutInLastStep);
    }

    @Step("Проверяем, что кнопка 'Оформить заказ' в чекауте активна")
    default void checkSubmitFromCheckoutActive() {
        waitAction().shouldBeClickable(submitCheckoutInLastStep);
    }
}
