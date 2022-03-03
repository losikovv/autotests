package ru.instamart.reforged.business.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BAuthModalCheck extends Check, B2BAuthModalElement {

    @Step("Проверяем, что модальное окно видимо и готово к работе")
    default void checkModalIsVisible() {
        waitAction().shouldBeVisible(modal);
        waitAction().shouldNotBeAnimated(modal);
    }

    @Step("Проверяем, что модальное окно скрыто")
    default void checkModalIsNotVisible() {
        waitAction().shouldNotBeVisible(modal);
    }
}
