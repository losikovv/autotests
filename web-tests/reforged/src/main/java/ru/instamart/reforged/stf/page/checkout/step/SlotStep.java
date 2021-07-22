package ru.instamart.reforged.stf.page.checkout.step;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class SlotStep {

    private final Element slotsSpinner = new Element(By.xpath("//div[@class = 'windows-selector-content']//div[contains(@class, 'Spinner')]"));
    private final Element firstActiveSlot = new Element(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']"));
    private final Element firstActiveSlotTime = new Element(By.xpath("//div[(contains(@id, 'deliveryDay'))]//div[@class='windows-selector-item']//span[(contains(@class, 'interval'))]"));
    private final Button submit = new Button(By.xpath("//button[@data-qa='checkout_delivery_submit_button']"));
    private final Element deliveryPlaceholder = new Element(By.xpath("//div[text()='Интервалы доставки недоступны']"));

    @Step("Выбрать первый активный слот")
    public void setFirstActiveSlot() {
        firstActiveSlot.click();
    }

    @Step("Нажать продолжить")
    public void clickToSubmit() {
        submit.click();
    }
}
