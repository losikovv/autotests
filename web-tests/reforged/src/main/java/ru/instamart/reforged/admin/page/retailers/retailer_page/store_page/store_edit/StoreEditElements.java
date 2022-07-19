package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Selector;

public interface StoreEditElements {

    Selector regionsDropdown = new Selector(By.xpath("//div[@data-qa='operationalZoneId']"), "Выпадающий список доступных регионов");
    ElementCollection storeGroups = new ElementCollection(By.xpath("//div[@data-qa='labelIdsSbermarket']/label/span[2]"), "Список групп магазинов");
    ElementCollection selectedStoreGroups = new ElementCollection(By.xpath("//div[@data-qa='labelIdsSbermarket']/label[contains(@class,'checked')]/span[2]"), "Выбранные группы магазина");

    Button submit = new Button(By.xpath("//button[@type='submit']"), "Кнопка 'Изменить'");
}
