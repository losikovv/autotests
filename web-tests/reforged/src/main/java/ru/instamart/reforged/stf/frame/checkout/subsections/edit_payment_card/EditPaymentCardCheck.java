package ru.instamart.reforged.stf.frame.checkout.subsections.edit_payment_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface EditPaymentCardCheck extends Check, EditPaymentCardElement {

    @Step("Проверяем, что модальное окно не анимировано")
    default void checkModalNotAnimated() {
        editPaymentCardModal.should().animationFinished();
    }
}
