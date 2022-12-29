package ru.instamart.reforged.chatwoot.frame.date_time_picker;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface DateTimePickerElement {
    Element dateTimePicker = new Element(By.xpath("//div[@class='ant-picker-datetime-panel']"), "Виджет выбора даты и времени 'отложить до'");
    Element firstDayOfThisMonth = new Element(By.xpath("//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-start')]"), "Первый день текущего месяца в календаре");

    Element firstDayOfNextMonth = new Element(By.xpath("(//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-start')])[2]"), "Первый день следующего месяца в календаре");

    Element today = new Element(By.xpath("//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-today')]"), "Текущий день в календаре");
    Element tomorrow = new Element(By.xpath("//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-today')]/following-sibling::td"), "Следующий за текущим день в календаре");
    Element lastDayOfPreviousMonth = new Element(By.xpath("(//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-end')])[1]"), "Последний день предыдущего месяца в календаре");

    Element lastDayOfThisMonth = new Element(By.xpath("(//div[@class='ant-picker-datetime-panel']//td[contains(@class,'ant-picker-cell-end')])[2]"), "Последний день текущего месяца в календаре");

    Button okDateTime = new Button(By.xpath("//li[@class='ant-picker-ok']/button"), "Кнопка 'Ок' виджета выбора даты/времени");
}

