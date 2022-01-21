package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface StorePageElements {

    Element storeTitle = new Element(By.xpath("//header//h4"), "Тайтл магазина");

    Button backToStoresList = new Button(By.xpath("//span[contains(text(), 'Список магазинов')]/ancestor::button"), "Кнопка возвращения к списку магазинов");
    Button storeZones = new Button(By.xpath("//span[contains(text(), 'Зоны')]/ancestor::button"), "Кнопка зон магазинов");
}
