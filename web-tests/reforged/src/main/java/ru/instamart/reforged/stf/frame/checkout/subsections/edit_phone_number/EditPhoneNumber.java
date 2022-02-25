package ru.instamart.reforged.stf.frame.checkout.subsections.edit_phone_number;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.CommonFrameButtons;

public class EditPhoneNumber implements CommonFrameButtons, EditPhoneNumberCheck {

    @Step("Заполнить номер телефона")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Очистить поле номер")
    public void clearPhoneNumber() {
        phoneNumber.clear();
    }
}
