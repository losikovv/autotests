package ru.instamart.reforged.business.page.checkout.replacementPolicyStep;

import io.qameta.allure.Step;

public class ReplacementPolicyStep implements ReplacementPolicyCheck {

    @Step("Выбираем способ замены: {0}")
    public void clickToPolicy(String policy) {
        replacementPolicy.clickOnElementWithText(policy);
    }

    @Step("Нажимаем 'Продолжить'")
    public void clickToSubmit() {
        submit.scrollTo();
        submit.hoverAndClick();
    }
}
