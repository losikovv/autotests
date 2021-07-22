package ru.instamart.reforged.admin.page.login;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface LoginCheck extends Check, LoginElement {

    @Step("Проверяем что на странице отображается заголовок Вход")
    default void checkTitle() {
        Kraken.waitAction().shouldBeVisible(title);
    }

    @Step("Проверяем текст сообщения об ошибке с пустым полем username")
    default void checkErrorEmptyEmail() {
        Kraken.waitAction().shouldBeVisible(errorSetEmail);
    }

    @Step("Проверяем текст сообщения об ошибке с неверным форматом для поля username")
    default void checkErrorInvalidEmail() {
        Kraken.waitAction().shouldBeClickable(errorInvalidFormatEmail);
    }

    @Step("Проверяем текст сообщения об ошибке с пустым полем password")
    default void checkErrorEmptyPassword() {
        Kraken.waitAction().shouldBeVisible(errorSetPassword);
    }

    @Step("Проверяем текст сообщения об ошибке с коротким значением для поля password")
    default void checkErrorShortPassword() {
        Kraken.waitAction().shouldBeVisible(errorShortPassword);
    }

    @Step("Проверяем текст сообщения об ошибке для несуществующего пользователя")
    default void checkErrorInvalidEmailOrPassword() {
        Kraken.waitAction().shouldBeVisible(errorInvalidEmailOrPassword);
    }

    @Step("Проверяем, что на странице появилась нотификации 'У вас недостаточно прав'")
    default void checkPermissionError() {
        Kraken.waitAction().shouldBeVisible(errorNoPermission);
    }
}
