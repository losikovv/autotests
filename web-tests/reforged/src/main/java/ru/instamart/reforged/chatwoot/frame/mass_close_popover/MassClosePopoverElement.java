package ru.instamart.reforged.chatwoot.frame.mass_close_popover;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.frame.date_time_picker.DateTimePicker;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface MassClosePopoverElement {

    DateTimePicker dateTimePicker = new DateTimePicker();

    Element massClosePopover = new Element(By.xpath("//div[contains(@class,'ant-popover-content')][contains(.,'Тематика')]"), "Всплывающее меню массового закрытия обращений");

    Input topicInput = new Input(By.xpath("//input[@id='topic']"), "Поле ввода 'Тематика'");
    ElementCollection topicsList = new ElementCollection(By.xpath("//div[contains(@class,'ant-select-item ant-select-item-option')]"), "Выпадающий список тематик обращений");
    Element topicByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ant-select-item-option')][contains(@title,'%s')]"), "Тематика по названию");

    Input massCloseDateTimeFrom = new Input(By.xpath("//input[@placeholder='С']"), "Поле ввода Дата создания 'С' формы массового закрытия чатов");
    Input massCloseDateTimeTo = new Input(By.xpath("//input[@placeholder='По']"), "Поле ввода Дата создания 'По' формы массового закрытия чатов");
    Input massCloseMessage = new Input(By.xpath("//textarea[@id='message']"), "Поле ввода 'Сообщение для рассылки' формы массового закрытия чатов");
    Button massCloseGetFilteredChatsCount = new Button(By.xpath("//button[contains(.,'Запросить кол-во закрываемых диалогов')]"), "Кнопка 'Запросить количество закрываемых диалогов' формы массового закрытия чатов");
    Element filteredChatsCountMessage = new Element(By.xpath("//div[@class='ant-form-item-control-input-content'][contains(.,'Будет завершен')]"), "Сообщение 'Будет завершено ХХ диалогов'");
    Button submitMassClose = new Button(By.xpath("//div[contains(@class,'ant-popover-content')]//button[@type='submit']"), "Кнопка 'Сохранить' формы массового закрытия чатов");
}

