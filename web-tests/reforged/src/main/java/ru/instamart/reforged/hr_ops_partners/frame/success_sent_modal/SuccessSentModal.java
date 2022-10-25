package ru.instamart.reforged.hr_ops_partners.frame.success_sent_modal;

import io.qameta.allure.Step;

public class SuccessSentModal implements SuccessSentModalCheck {

    @Step("Нажимаем 'Х' (закрыть)")
    public void close() {
        close.click();
    }
}
