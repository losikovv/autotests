package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthoredHeaderCheck extends Check, AuthoredHeaderElement {

    @Step("Проверяем, что кнопка профиля не видна")
    default void checkProfileButtonNotVisible() {
        waitAction().shouldNotBeVisible(profile);
    }

    @Step("Проверяем, что кнопка профиля видна")
    default void checkProfileButtonVisible() {
        waitAction().shouldBeVisible(profile);
    }

    @Step("Проверяем, что кнопка логина видна")
    default void checkLoginIsVisible() {
        waitAction().shouldBeVisible(login);
    }

    @Step("Проверяем, что отображается введенный адрес")
    default void checkEnteredAddressIsVisible() {
        waitAction().shouldBeVisible(enteredAddress);
    }

    @Step("Проверяем, что не отображается введенный адрес")
    default void checkEnteredAddressNotVisible() {
        waitAction().shouldNotBeVisible(enteredAddress);
    }
}
