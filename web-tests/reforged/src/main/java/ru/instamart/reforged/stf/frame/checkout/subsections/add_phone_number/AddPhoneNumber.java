package ru.instamart.reforged.stf.frame.checkout.subsections.add_phone_number;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.CommonFrameButtons;

public class AddPhoneNumber implements CommonFrameButtons, AddPhoneNumberCheck {

    @Step("Заполнить номер телефона {0}")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Очистить поле номер телефона")
    public void clearPhoneNumber() {
        phoneNumber.clear();
    }
}
