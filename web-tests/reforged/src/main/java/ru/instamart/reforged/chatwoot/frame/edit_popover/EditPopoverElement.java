package ru.instamart.reforged.chatwoot.frame.edit_popover;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface EditPopoverElement {
    Element editPopover = new Element(By.xpath("//div[contains(@class,'ant-popover-content')][contains(.,'Номер доставки')]"), "Всплывающее меню редактирования обращения");
    Input shipmentInput = new Input(By.xpath("//input[@id='shipmentNumber']"), "Поле ввода 'Номер доставки'");
    Input topicInput = new Input(By.xpath("//input[@id='topic']"), "Поле ввода 'Тема'");
    ElementCollection topicsList = new ElementCollection(By.xpath("//div[contains(@class,'ant-select-item ant-select-item-option')]"), "Выпадающий список тем обращений");
    Element topicByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ant-select-item-option')][contains(@title,'%s')]"), "Тема по названию");
    Button resetEdit = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='reset']"), "Кнопка 'Отмена'");
    Button submitEdit = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='submit']"), "Кнопка 'Сохранить'");
}
