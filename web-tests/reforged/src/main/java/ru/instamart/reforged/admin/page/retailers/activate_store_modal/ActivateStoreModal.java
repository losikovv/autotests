package ru.instamart.reforged.admin.page.retailers.activate_store_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public class ActivateStoreModal implements Close, ActivateStoreModalCheck {

    @Step("Нажать на кнопку 'Ok' на модалке активации магазина")
    public void clickOnOkButton() {
        okButton.click();
    }
}
