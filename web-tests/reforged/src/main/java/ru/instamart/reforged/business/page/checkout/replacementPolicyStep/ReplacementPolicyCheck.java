package ru.instamart.reforged.business.page.checkout.replacementPolicyStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ReplacementPolicyCheck extends Check, ReplacementPolicyElement {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите способ осуществления замен' видна")
    default void checkSubmitButtonVisible() {
        waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите способ осуществления замен' не видна")
    default void checkSubmitButtonNotVisible() {
        waitAction().shouldNotBeVisible(submit);
    }

    @Step("Проверяем что спиннер в разделе 'Выберите способ осуществления замен' не виден")
    default void checkReplacementSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем что спиннер в разделе 'Выберите способ осуществления замен' виден")
    default void checkReplacementSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }
}