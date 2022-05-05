package ru.instamart.reforged.stf.frame.address;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface AddressLargeElement {
    //Тикет на добавление data-qa атрибутов https://jira.sbmt.io/browse/B2C-8801

    Element addressModal = new Element(By.xpath("//ymaps[contains(@class,'-inner-panes')]"), "Яндекс-карты");
    Element markerSelectOnMap = new Element(By.xpath("(//div[contains(.,'Адрес можно выбирать прямо на карте')])[last()]"), "Маркер с предложением выбрать адрес на карте");

    Button byDelivery = new Button(By.xpath("//main//button[@aria-controls='by_courier-tab']"), "Кнопка 'Доставка'");
    Button byPickup = new Button(By.xpath("//main//button[@aria-controls='pickup-tab']"), "Кнопка 'Самовывоз'");

    Input addressInput = new Input(By.xpath("//ymaps/..//input[@placeholder='Ваш адрес']"), "Инпут ввода адреса");
    Button clearInput = new Button(By.xpath("//ymaps/..//input[@placeholder='Ваш адрес']/..//i"), "Кнопка очистки инпута");
    DropDown foundedAddresses = new DropDown(By.xpath("//ymaps/..//input[@placeholder='Ваш адрес']/../div/div/div"), "Дропдаун с предложенными адресами");
    Element outOfDeliveryMessage = new Element(By.xpath("//div[contains(.,'К вам пока не привозят. Попробуйте')][./button[contains(.,'самовывоз')]]"), "Сообщение, если адрес вне зон доставки");

    Button save = new Button(By.xpath("//button[contains(.,'Сохранить')]"), "Кнопка 'Сохранить'");
    Button findStores = new Button(By.xpath("//button[contains(.,'Найти магазины')]"), "Кнопка 'Найти магазины'");
}
