package ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditPromoCodeElement {

    Element promoCodeModalHeader = new Element(By.xpath("//div[contains(@class, 'modal-form__title') and text()='Введите промокод']"), "Заголовок 'Введите заголовок' на модальном окне ввода промокода");

    Input promoCodeInput = new Input(By.xpath("//input[@name = 'couponCode']"), "Инпут ввода промокода на модальном окне ввода промокода");
    Button promoCodeApplyButton = new Button(By.xpath("//button[@data-qa='checkout_coupon_modal_add_button']"), "Кнопка применения промокода на модальном окне промокода");
    Button promoCodeCancelButton = new Button(By.xpath("//button[@data-qa='checkout_coupon_modal_cancel_button']"), "Кнопка отмены ввода промокода на модальном окне промокода");

}
