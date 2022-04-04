package ru.instamart.reforged.sber_id_auth.auth_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface SberIdAuthCheck extends Check, SberIdAuthElement {

    @Step("Проверяем, что отображается поле ввода номера телефона")
    default void checkPhoneInputVisible() {
        Kraken.waitAction().shouldBeVisible(phoneNumber);
    }
}
