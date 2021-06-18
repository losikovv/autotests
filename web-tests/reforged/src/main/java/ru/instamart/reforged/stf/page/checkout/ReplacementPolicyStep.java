package ru.instamart.reforged.stf.page.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;

public class ReplacementPolicyStep {

    private final ElementCollection replacementPolicy = new ElementCollection(By.xpath("//div[@class = 'replacement-policy__desc']"));
    private final Button submit = new Button(By.xpath("//button[@data-qa='checkout_replacement_policy_submit_button']"));

    @Step("Выбрать способ {0}")
    public void clickToPolicy(String policy) {
        replacementPolicy.clickOnElementWithText(policy);
    }

    @Step("Нажать на Продолжить")
    public void clickToSubmit() {
        submit.click();
    }
}
