package ru.instamart.reforged.next.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthModalCheck extends Check, AuthModalElement {

    @Step("Проверяем, что ошибка ввода номера телефона видима")
    default void checkPhoneErrorIsVisible() {
        waitAction().shouldBeVisible(phoneError);
    }

    @Step("Проверяем, что модальное окно видимо и готово к работе")
    default void checkModalIsVisible() {
        waitAction().shouldBeVisible(modalLogin);
        waitAction().shouldNotBeAnimated(modalLogin);
    }

    @Step("Проверяем, что модальное окно скрыто")
    default void checkModalIsNotVisible() {
        waitAction().shouldNotBeVisible(modalLogin);
    }

    @Step("Проверяем, что отображается окно подтверждения номера телефона")
    default void checkModalConfirmPhoneIsVisible() {
        waitAction().shouldBeVisible(modalConfirmPhone);
    }

    @Step("Проверяем, что модальное окно скрыто")
    default void checkPhoneInputIsClickable() {
        waitAction().shouldBeClickable(phoneField);
    }

    @Step("Проверяем, что кнопка входа по SberID для бизнеса показана")
    default void checkSberBusinessIdIsVisible() {
        waitAction().shouldBeVisible(sberBusinessIdButton);
    }

    @Step("Проверяем, что кнопка входа по SberID для бизнеса скрыта")
    default void checkSberBusinessIdIsNotVisible() {
        waitAction().shouldNotBeVisible(sberBusinessIdButton);
    }

    @Step("Проверяем, что кнопка через SberID показана")
    default void checkSberIdIsVisible() {
        waitAction().shouldBeVisible(sberId);
    }

    @Step("Проверяем, что кнопка входа через ВК показана")
    default void checkVkIsVisible() {
        waitAction().shouldBeVisible(vkontakte);
    }

    @Step("Проверяем, что кнопка входа через facebook показана")
    default void checkFacebookIsVisible() {
        waitAction().shouldBeVisible(facebook);
    }

    @Step("Проверяем, что кнопка через mail показана")
    default void checkMailRuIsVisible() {
        waitAction().shouldBeVisible(mailRu);
    }
}
