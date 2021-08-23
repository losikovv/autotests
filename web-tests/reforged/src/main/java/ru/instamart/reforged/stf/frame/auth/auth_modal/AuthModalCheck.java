package ru.instamart.reforged.stf.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthModalCheck extends Check, AuthModalElement {

    @Step("Проверяем, что ошибка ввода номера телефона видима")
    default void checkPhoneErrorIsVisible() {
        waitAction().shouldBeVisible(phoneError);
    }

    @Step("Проверяем, что модальное окно видимо")
    default void checkModalIsVisible() {
        waitAction().shouldBeVisible(modal);
    }

    @Step("Проверяем, что модальное окно скрыто")
    default void checkModalIsNotVisible() {
        waitAction().shouldNotBeVisible(modal);
    }
}
