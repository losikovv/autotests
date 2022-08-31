package ru.instamart.reforged.admin.page.shoppers.create;

import io.qameta.allure.Step;

public interface ShoppersCreateCheck extends ShoppersCreateElement {

    @Step("Проверить валидацию для обязательного поля '{0}'")
    default void checkRequiredFieldValidation(final String fieldName) {
        requiredFieldError.should().visible(fieldName);
    }

    @Step("Проверить валидацию для поля 'Пароль'")
    default void checkRequiredPasswordValidation() {
        requiredPasswordError.should().visible();
    }
}
