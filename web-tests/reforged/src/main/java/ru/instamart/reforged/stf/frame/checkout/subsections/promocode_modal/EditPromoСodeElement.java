package ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditPromoСodeElement {

    Element promoCodeModalHeader = new Element(By.xpath("//div[contains(@class, 'modal-form__title') and text()='Введите промокод']"), "Заголовок 'Введите заголовок' на модальном окне ввода промокода");
    Element closePromoCodeModalButton = new Element(By.xpath("//header//div[contains(@class, 'Button')]"), "Кнопка закрытия модального окна ввода промокода");

    Input promoCodeInput = new Input(By.xpath("//input[@name = 'couponCode']"), "Инпут ввода промокода на модальном окне ввода промокода");
    Button promoCodeApplyButton = new Button(By.xpath("//button[@data-qa='checkout_coupon_modal_add_button']"));
}
