package ru.instamart.reforged.next.frame.checkout.subsections.edit_payment_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditPaymentCardCheck extends Check, EditPaymentCardElement {

    @Step("Проверяем, что модальное окно не анимировано")
    default void checkModalNotAnimated() {
        waitAction().shouldNotBeAnimated(editPaymentCardModal);
    }
}
