package ru.instamart.reforged.sber_payments.rbsuat_payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface RbsuatPaymentsCheck extends Check, RbsuatPaymentsElement {

    @Step("Проверяем, что отображается поле ввода пароля")
    default void checkPasswordInputVisible() {
        Kraken.waitAction().shouldBeVisible(passwordInput);
    }
}
