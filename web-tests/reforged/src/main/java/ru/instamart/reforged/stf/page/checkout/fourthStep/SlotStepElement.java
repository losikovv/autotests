package ru.instamart.reforged.stf.page.checkout.fourthStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface SlotStepElement {

    Element slotsSpinner = new Element(By.xpath("//div[@class='checkout-panel checkout-panel--active']//span[contains(text(),'Загрузка')] "), "Спиннер в слотах доставки");
    Element firstActiveSlotSecondRetailer = new Element(By.xpath("//div[@class='windows-selector-content']//div[@class='windows-selector-item']"), "Первый доступный слот второго ретейлера");
    ElementCollection activeSlots = new ElementCollection(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']"), "Коллекция активных слотов");
    Button choseAnotherTimeButton = new Button(By.xpath("//button[text()='Выбрать другое время']"),"Выбрать другое время");
    Element firstActiveSlotTime = new Element(By.xpath("//div[contains(@id, 'deliveryDay')]//div[@class='windows-selector-item']//span[contains(@class, 'interval')]"), "Первый доступный слот");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_delivery_submit_button']"), "empty");
    Element deliveryPlaceholder = new Element(By.xpath("//div[text()='Интервалы доставки недоступны']"), "empty");

    Element nextDayTab = new Element(By.xpath("//span[text()='Завтра']/parent::div"), "Вкладка слотов следующего дня");
}