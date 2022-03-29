package ru.instamart.reforged.admin.block.add_city;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface AddCityElement {

    Element modalTitle = new Element(By.xpath("//div[@class='ant-drawer-title']"), "Название модального окна");
    Element modal = new Element(By.xpath("//div[text()='Создать город']/ancestor::div[@class='ant-drawer-content-wrapper']"),
            "модальное окно создания города");

    Button closeButton = new Button(By.xpath("//button[@class='ant-drawer-close']"), "Кнопка закрытия модального окна");

    Element cityNameInputLabel = new Element(By.xpath("//label[@for='name']"), "Лейбл для ввода имени города в имен.падеже");
    Input cityNameInput = new Input(By.id("name"), "Инпут для ввода имени городав имен.падеже");

    Element cityNameInInputLabel = new Element(By.xpath("//label[@for='nameId']"), "Лейбл для ввода имени города в предл.падеже");
    Input cityNameInInput = new Input(By.id("nameId"), "Инпут ввода имени города в предл.падеже");

    Element cityNameFromInputLabel = new Element(By.xpath("//label[@for='nameFrom']"), "Лейбл для ввода имени города в родит.падеже");
    Input cityNameFromInput = new Input(By.id("nameFrom"), "Инпут ввода имени города в родит.падеже");

    Element cityNameToInputLabel = new Element(By.xpath("//label[@for='nameTo']"), "Лейбл для ввода имени города в направит.падеже");
    Input cityNameToInput = new Input(By.id("nameTo"), "Инпут ввода имени города в направит.падеже");

    Element cityLinkLabel = new Element(By.xpath("//label[@for='slug']"), "Лейбл для ввода ссылки на город");
    Input cityLinkInput = new Input(By.id("slug"), "Инпут ввода ссылки на город");

    Button createButton = new Button(By.xpath("//span[text()='Сохранить']/parent::button"), "Кнопка подтверждения добавления нового города");
    Button cancelButton = new Button(By.xpath("//span[text()='Отменить']/parent::button"), "Кнопка отмены добавления нового города");
}
