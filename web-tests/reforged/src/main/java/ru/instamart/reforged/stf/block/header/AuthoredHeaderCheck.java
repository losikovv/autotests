package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthoredHeaderCheck extends Check, AuthoredHeaderElement {

    @Step("Проверяем, что кнопка профиля не видна = юзер не залогинен")
    default void checkProfileButtonNotVisible() {
        waitAction().shouldNotBeVisible(profile);
    }

    default void checkLoginIsVisible() {
        waitAction().shouldBeVisible(login);
    }
}
