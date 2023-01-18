package ru.instamart.reforged.stf.frame.auth.phone_confirm_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface PhoneConfirmModalCheck extends Check, PhoneConfirmModalElement {

    @Step("Проверяем, что отображается окно подтверждения номера телефона")
    default void checkModalConfirmPhoneIsVisible() {
        waitAction().shouldBeVisible(modalConfirmPhone);
    }

}
