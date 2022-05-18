package ru.instamart.reforged.next.page.checkout.fifthStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface PaymentStepCheck extends Check, PaymentStepElement {

    @Step("Проверяем, что кнопка заказа некликабельна")
    default void checkSubmitOrderButtonNotClickable() {
        waitAction().shouldNotBeClickable(submitFromCheckoutColumn);
    }
}