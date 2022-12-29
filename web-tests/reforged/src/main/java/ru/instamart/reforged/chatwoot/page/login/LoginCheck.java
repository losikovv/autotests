package ru.instamart.reforged.chatwoot.page.login;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface LoginCheck extends Check, LoginElement {

    @Step("Проверяем значение в поле 'email': '{expectedValue}'")
    default void checkEmailInputValue(final String expectedValue){
       Assert.assertEquals(emailInput.getValue(), expectedValue, "Текущее значение в поле email отличается от ожидаемого");
    }

    @Step("Проверяем значение в поле 'пароль': '{expectedValue}'")
    default void checkPasswordInputValue(final String expectedValue){
        Assert.assertEquals(passwordInput.getValue(), expectedValue, "Текущее значение в поле 'пароль' отличается от ожидаемого");
    }

    @Step("Проверяем что кнопка вход задисейблена")
    default void checkSubmitDisabled() {
        submit.should().unclickable();
    }
}
