package ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditLoyaltyCardElement {

    Element modal = new Element(By.xpath("//div[@class='modal-form']"), "модальное окно");
    Input value = new Input(By.xpath("//div[@class='modal-form']//input[contains(@class, 'checkout-input')]"), "Инпут ввода номера карты лояльности на модальном окне");
}
