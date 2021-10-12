package ru.instamart.reforged.stf.frame.checkout.subsections.retailer_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.CommonFrameButtons;

public final class EditRetailerCard implements CommonFrameButtons, EditRetailerCardCheck {

    @Step("Заполнить поле номер карты ретейлера {0}")
    public void fillValue(final String data) {
        value.fill(data);
    }
}
