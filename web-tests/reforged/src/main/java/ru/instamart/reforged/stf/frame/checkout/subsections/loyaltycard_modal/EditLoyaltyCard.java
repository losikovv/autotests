package ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.CommonFrameButtons;

public class EditLoyaltyCard implements CommonFrameButtons, EditLoyaltyCardCheck {

    @Step("Заполнить поле номер бонусной карты {0}")
    public void fillValue(String data) {
        value.fill(data);
    }
}
