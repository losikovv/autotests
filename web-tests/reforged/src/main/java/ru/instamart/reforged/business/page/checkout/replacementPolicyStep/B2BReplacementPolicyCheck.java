package ru.instamart.reforged.business.page.checkout.replacementPolicyStep;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BReplacementPolicyCheck extends Check, B2BReplacementPolicyElement {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите способ осуществления замен' видна")
    default void checkSubmitButtonVisible() {
        waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Выберите способ осуществления замен' не видна")
    default void checkSubmitButtonNotVisible() {
        Assert.assertTrue(submit.is().invisible());
    }

    @Step("Проверяем что спиннер в разделе 'Выберите способ осуществления замен' не виден")
    default void checkReplacementSpinnerNotVisible() {
        Assert.assertTrue(spinner.is().invisible());
    }

    @Step("Проверяем что спиннер в разделе 'Выберите способ осуществления замен' виден")
    default void checkReplacementSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }
}