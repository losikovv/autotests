package ru.instamart.reforged.business.page.checkout.slotsStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BSlotStepCheck extends Check, B2BSlotStepElement {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите время получения' видна")
    default void checkSubmitButtonVisible() {
        waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите время получения' не видна")
    default void checkSubmitButtonNotVisible() {
        waitAction().shouldNotBeVisible(submit);
    }

    @Step("Проверяем виден спиннер в разделе слотов")
    default void checkSlotsSpinnerIsVisible() {
        waitAction().shouldBeVisible(slotsSpinner);
    }

    @Step("Проверяем не виден спиннер в разделе слотов")
    default void checkSlotsSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(slotsSpinner);
    }

}