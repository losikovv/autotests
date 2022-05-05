package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Selector;

public interface StoreEditElements {

    Selector regionsDropdown = new Selector(By.id("store_operational_zone_id"), "Выпадающий список доступных регионов");

    ElementCollection storeGroups = new ElementCollection(By.xpath("(//div[./legend[contains(.,'Группы (характеристики)')]])[1]/div"), "Список групп магазинов");
    ElementCollection selectedStoreGroups = new ElementCollection(By.xpath("(//div[./legend[contains(.,'Группы (характеристики)')]])[1]/div[.//input[@checked]]"), "Выбранные группы магазина");

    Button submit = new Button(By.xpath("//button[@type='submit']"), "Кнопка 'Изменить'");
}
