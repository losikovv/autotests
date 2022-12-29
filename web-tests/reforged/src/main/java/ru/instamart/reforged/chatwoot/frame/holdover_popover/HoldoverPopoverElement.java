package ru.instamart.reforged.chatwoot.frame.holdover_popover;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.frame.date_time_picker.DateTimePicker;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface HoldoverPopoverElement {

    DateTimePicker dateTimePicker = new DateTimePicker();
    Element editPopover = new Element(By.xpath("//div[contains(@class,'ant-popover-content')][contains(.,'Номер доставки')]"), "Всплывающее меню редактирования обращения");
    Input topicInput = new Input(By.xpath("//input[@id='topic']"), "Поле ввода 'Тема'");
    ElementCollection topicsList = new ElementCollection(By.xpath("//div[contains(@class,'ant-select-item ant-select-item-option')]"), "Выпадающий список тем обращений");
    Element topicByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ant-select-item-option')][contains(@title,'%s')]"), "Тема по названию");
    Button resetEdit = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='reset']"), "Кнопка 'Отмена'");
    Button submitEdit = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='submit']"), "Кнопка 'Сохранить'");
    Element holdoverPopover = new Element(By.xpath("//div[contains(@class,'ant-popover-content')][contains(.,'Отложить до')]"), "Всплывающее меню 'отложить до..'");
    Input holdoverDateTimeInput = new Input(By.xpath("//input[@id='snoozedUntil']"), "Поле ввода даты/времени 'Отложить до'");
    Button submitHoldover = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='submit'][contains(.,'Отложить')]"), "Кнопка 'Отложить'");
    Element holdoverLabelInHeader = new Element(By.xpath("//div[@id='issue-details']//span[@class='ant-tag'][contains(.,'Приостановлено до ')]"), "Пометка о приостановке чата в хедере");
}

