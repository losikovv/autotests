package ru.instamart.reforged.hr_ops_partners.frame.regions_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface RegionsModalElement {

    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton_')]"), "Кнопка 'X' (закрыть)");
    Element modalTitle = new Element(By.xpath("//div[contains(@class,'RegionsModal_title')]"), "Заголовок окна 'Выберите ваш город'");
    Input regionInput = new Input(By.xpath("//input[contains(@class,'RegionsModal_input')]"), "Поле ввода названия города");
    ElementCollection regionsList = new ElementCollection(By.xpath("//li[contains(@class,'RegionsModal_item')]"), "Список городов");
    Element emptyListPlaceholder = new Element(By.xpath("//div[contains(@class,'RegionsModal_empty')]"), "Плейсхолдер пустого списка регионов");
}
