package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HomeCheck extends Check, HomeElement {

    @Step("Проверяем, что кнопка логина видима")
    default void checkLoginButtonIsVisible() {
        waitAction().shouldBeVisible(loginButton);
    }
}
