package ru.instamart.reforged.business.page.checkout.contactsStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface B2BContactsStepCheck extends B2BContactsStepElement, Check {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Контакты' видна")
    default void checkSubmitButtonVisible() {
        Kraken.waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Контакты' не видна")
    default void checkSubmitButtonNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(submit);
    }
}