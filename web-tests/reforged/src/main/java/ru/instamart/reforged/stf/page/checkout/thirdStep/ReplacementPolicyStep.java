package ru.instamart.reforged.stf.page.checkout.thirdStep;

import io.qameta.allure.Step;

public class ReplacementPolicyStep implements ReplacementPolicyElement {

    @Step("Выбрать способ замены: {0}")
    public void clickToPolicy(String policy) {
        replacementPolicy.clickOnElementWithText(policy);
    }

    @Step("Нажать на Продолжить")
    public void clickToSubmit() {
        submit.scrollTo();
        submit.hoverAndClick();
    }
}
