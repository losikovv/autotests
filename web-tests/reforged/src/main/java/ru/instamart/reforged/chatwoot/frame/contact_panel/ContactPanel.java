package ru.instamart.reforged.chatwoot.frame.contact_panel;

import io.qameta.allure.Step;

public final class ContactPanel implements ContactPanelCheck {

    @Step("Нажимаем 'Редактировать'")
    public void clickLogout() {
        editContact.click();
    }
}
