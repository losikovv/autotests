package ru.instamart.reforged.business.page.checkout.slotsStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface SlotStepElement {

    Element slotsSpinner = new Element(By.xpath("//div[@class='checkout-panel checkout-panel--active']//span[contains(text(),'Загрузка')] "), "Спиннер активного шага чекаута");
    Element firstActiveSlot = new Element(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']"), "Первый доступный слот доставки");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_delivery_submit_button']"), "Кнопка 'Продолжить' шага 'Выберите время получения'");
}