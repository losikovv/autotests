package ru.instamart.reforged.business.frame.auth.auth_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BAuthModalCheck extends Check, B2BAuthModalElement {

    @Step("Проверяем, что модальное окно видимо и готово к работе")
    default void checkModalIsVisible() {
        waitAction().shouldBeVisible(modal);
        Assert.assertTrue(modal.is().animationFinished());
    }

    @Step("Проверяем, что модальное окно скрыто")
    default void checkModalIsNotVisible() {
        Assert.assertTrue(modal.is().invisible());
    }
}
