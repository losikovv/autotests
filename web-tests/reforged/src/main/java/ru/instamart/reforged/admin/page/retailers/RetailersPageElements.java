package ru.instamart.reforged.admin.page.retailers;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RetailersPageElements {

    Button addNewRetailerButton = new Button(By.xpath("//button[@class='ant-btn ant-btn-primary']"), "Кнопка добавления нового ретейлера");

    ElementCollection retailersInTable = new ElementCollection(By.xpath("//div[@data-qa='table_cell_retailer_logo_name']/span"), "Строки ретейлеров в таблице");
    ElementCollection retailersAccessibilityInTable = new ElementCollection(By.xpath("//tr[contains(@class, 'level-0')]//span[contains(text(), 'Доступен') or contains(text(), 'Недоступен')]"), "Записи о доступности ретейлеров в таблице");
    ElementCollection retailersCreateDateInTable = new ElementCollection(By.xpath("//td[4]"), "Даты создания ретейлеров в таблице");

    Element retailerInaccessibilityInTable = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::tr//span[text() = 'Недоступен']"), "Недоступность определенного ретейлера в таблице");
    Element retailerAccessibilityInTable = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::tr//span[text() = 'Доступен']"), "Недоступность определенного ретейлера в таблице");
}
