package ru.instamart.reforged.next.frame.checkout.subsections.edit_phone_number;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditPhoneNumberElement {

    Input phoneNumber = new Input(By.xpath("//input[@id='phone-input']"), "поле для ввода телефона");
    Element phoneEditModal = new Element(By.xpath("//div[@id='ModalPhoneForm']"), "Модальное окно редактирования номера телефона");
}
