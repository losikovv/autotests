package ru.instamart.reforged.stf.frame.checkout.subsections.add_phone_number;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface AddPhoneNumberElement {

    Input phoneNumber = new Input(By.xpath("//input[@id='phone-input']"), "поле для ввода телефона");
    Element phoneAddModal = new Element(By.xpath("//div[@id='ModalPhoneForm']"), "Модальное окно добавления номера телефона");
}
