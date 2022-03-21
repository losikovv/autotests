package ru.instamart.reforged.sber_payments.rbsuat_payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.page.Page;

/**
 * Страница подтверждения платежа для шлюза оплаты картой онлайн 'sber'
 */
public class RbsuatPaymentsPage implements Page, RbsuatPaymentsCheck {

    @Step("Вводим пароль: '{password}'")
    public void fillPassword(final String password) {
        passwordInput.fill(password);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
