package ru.instamart.reforged.stf.page.checkout.thirdStep;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ReplacementPolicyCheck extends Check, ReplacementPolicyElement {

    @Step("Проверяем что спиннер в разделе замен не виден")
    default void checkReplacementSpinnerNotVisible() {
        Assert.assertTrue(spinner.is().invisible());
    }

    @Step("Проверяем что спиннер в разделе замен виден")
    default void checkReplacementSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }

    @Step("Проверяем что кнопка 'продолжить' в разделе замен видна")
    default void checkSubmitVisible() {
        submit.should().visible();
        Assert.assertTrue(submit.is().animationFinished());
    }
}