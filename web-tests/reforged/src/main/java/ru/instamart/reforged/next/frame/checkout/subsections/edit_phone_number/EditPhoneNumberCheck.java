package ru.instamart.reforged.next.frame.checkout.subsections.edit_phone_number;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditPhoneNumberCheck extends Check, EditPhoneNumberElement {

    @Step("Проверяем, что окно редактирования телефона закрыто")
    default void checkPhoneEditModalClosed() {
        waitAction().shouldNotBeVisible(phoneEditModal);
    }
}
