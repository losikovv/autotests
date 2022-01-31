package ru.instamart.reforged.business.page.checkout.replacementPolicyStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface ReplacementPolicyElement {

    ElementCollection replacementPolicy = new ElementCollection(By.xpath("//div[@class = 'replacement-policy__desc']/ancestor::div[contains(@class, 'replacement-policy')]"), "Коллекция элементов политики замены");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_replacement_policy_submit_button']"), "Кнопка 'Продолжить' шага 'Выберите способ осуществления замен'");
    Element spinner = new Element(By.xpath("//div[@class='replacement-policies__content']//span[contains(text(), 'Загрузка')]"), "Спиннер шага 'Выберите способ осуществления замен'");
}