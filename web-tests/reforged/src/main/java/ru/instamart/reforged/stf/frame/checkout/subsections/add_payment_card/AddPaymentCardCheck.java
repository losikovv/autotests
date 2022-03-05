package ru.instamart.reforged.stf.frame.checkout.subsections.add_payment_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface AddPaymentCardCheck extends Check, AddPaymentCardElement {

    @Step("Проверка отображения ошибки '{0}'")
    default void checkValidationErrorVisible(final String errorText) {
        Kraken.waitAction().shouldBeVisible(cardError, errorText);
    }
}
