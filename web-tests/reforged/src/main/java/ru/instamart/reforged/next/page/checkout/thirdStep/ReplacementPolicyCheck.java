package ru.instamart.reforged.next.page.checkout.thirdStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ReplacementPolicyCheck extends Check, ReplacementPolicyElement {

    @Step("Проверяем что спиннер в разделе замен не виден")
    default void checkReplacementSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем что спиннер в разделе замен виден")
    default void checkReplacementSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }
}