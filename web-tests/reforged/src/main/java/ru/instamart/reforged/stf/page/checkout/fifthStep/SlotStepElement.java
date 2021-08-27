package ru.instamart.reforged.stf.page.checkout.fifthStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface SlotStepElement {

    Element slotsSpinner = new Element(By.xpath("//div[@class = 'windows-selector-content']//div[contains(@class, 'Spinner')]"));
    Element firstActiveSlot = new Element(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']"));
    Element firstActiveSlotTime = new Element(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']//span[(contains(@class, 'interval'))]"));
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_delivery_submit_button']"));
    Element deliveryPlaceholder = new Element(By.xpath("//div[text()='Интервалы доставки недоступны']"));

}