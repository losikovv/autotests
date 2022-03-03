package ru.instamart.reforged.business.frame.address;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface B2BAddressElement {

    Element addressModal = new Element(By.xpath("//div[@data-qa='address-modal']"), "Окно указания адреса");

    Input address = new Input(By.xpath("//input[@data-qa='address-modal-input']"), "Поле ввода адреса");
    DropDown dropDownAddress = new DropDown(By.xpath("//input[@data-qa='address-modal-input']/following::div[2]/div/div/div"), "Выпадающий список адресов");
    Button save = new Button(By.xpath("//button[@data-qa='address-modal-submit']"), "Кнопка 'Сохранить'");
    Element markerImageOnMapInAdvice = new Element(By.xpath("//div[contains(@class, 'notice')]/descendant::img[contains(@src, '/marker')]"), "Указатель на ЯндексКартах");
    Element ymapReady = new Element(By.xpath("//div[@data-qa='address-modal']//ymaps"), "Элемент ЯндексКарт, готовый к работе");
}
