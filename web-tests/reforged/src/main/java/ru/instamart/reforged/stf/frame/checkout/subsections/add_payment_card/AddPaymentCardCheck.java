package ru.instamart.reforged.stf.frame.checkout.subsections.add_payment_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface AddPaymentCardCheck extends Check, AddPaymentCardElement {

    @Step("Проверка отображения ошибки заполнения поля ввода '{0}'")
    default void checkValidationErrorVisible(final String errorText) {
        Kraken.waitAction().shouldBeVisible(cardInputError, errorText);
    }

    @Step("Проверка отображения ошибки добавления карты '{0}'")
    default void checkErrorAddingCardDisplayed(final String errorText) {
        Kraken.waitAction().shouldBeVisible(cardModalError, errorText);
    }
}
