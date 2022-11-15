package ru.instamart.reforged.stf.frame.checkout.subsections.edit_payment_card;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface EditPaymentCardCheck extends Check, EditPaymentCardElement {

    @Step("Проверяем, что модальное окно не анимировано")
    default void checkModalNotAnimated() {
        Assert.assertTrue(editPaymentCardModal.is().animationFinished());
    }
}
