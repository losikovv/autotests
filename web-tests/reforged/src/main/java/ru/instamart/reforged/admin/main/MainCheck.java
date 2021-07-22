package ru.instamart.reforged.admin.main;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface MainCheck extends Check, MainElement {

    @Step("Пользователь авторизовался")
    default void checkAuth() {
        Kraken.waitAction().shouldBeVisible(user);
    }

    @Step("Пользователь не авторизовался")
    default void checkIsNotAuth() {
        Kraken.waitAction().shouldNotBeVisible(user);
    }
}
