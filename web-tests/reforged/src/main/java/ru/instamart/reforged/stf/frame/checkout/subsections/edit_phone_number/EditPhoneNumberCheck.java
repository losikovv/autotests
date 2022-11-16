package ru.instamart.reforged.stf.frame.checkout.subsections.edit_phone_number;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface EditPhoneNumberCheck extends Check, EditPhoneNumberElement {

    @Step("Проверяем, что окно редактирования телефона закрыто")
    default void checkPhoneEditModalClosed() {
        phoneEditModal.should().invisible();
    }
}
