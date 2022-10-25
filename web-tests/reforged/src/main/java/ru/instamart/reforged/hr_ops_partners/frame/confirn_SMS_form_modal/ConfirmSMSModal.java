package ru.instamart.reforged.hr_ops_partners.frame.confirn_SMS_form_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;

public class ConfirmSMSModal implements ConfirmSMSModalCheck {

    @Step("Нажимаем 'Х' (закрыть)")
    public void close() {
        close.click();
    }

    @Step("Кликаем в поле 'Код из смс'")
    public void clickCode() {
        ThreadUtil.simplyAwait(1);
        codeInput.click();
    }

    @Step("Вводим в поле 'Код из смс' текст: '{inputText}'")
    public void fillCode(final String inputText) {
        codeInput.fill(inputText);
    }


}
