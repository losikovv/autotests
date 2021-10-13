package ru.instamart.reforged.stf.page.checkout.thirdStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;

public interface ReplacementPolicyElement {

    ElementCollection replacementPolicy = new ElementCollection(By.xpath("//div[@class = 'replacement-policy__desc']/ancestor::div[contains(@class, 'replacement-policy')]"), "Коллекция элементов политики замены");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_replacement_policy_submit_button']"));
}