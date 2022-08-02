package ru.instamart.reforged.stf.page.checkout_new.add_payment_card_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface AddPaymentCardCheck extends Check, AddPaymentCardElement {

    @Step("Проверяем, что модальное окно отображается")
    default void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(addCard);
    }

    @Step("Проверяем, что модальное окно не отображается")
    default void checkModalNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(addCard);
    }

    @Step("Проверка отображения ошибки заполнения поля ввода '{0}'")
    default void checkValidationErrorVisible(final String errorText) {
        Kraken.waitAction().shouldBeVisible(cardInputError, errorText);
    }

    @Step("Проверка отображения ошибки добавления карты '{0}'")
    default void checkErrorAddingCardDisplayed(final String errorText) {
        Kraken.waitAction().shouldBeVisible(cardModalError, errorText);
    }
}
