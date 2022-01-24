package ru.instamart.reforged.admin.page.retailers.retailer_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RetailerPageElements {

    ElementCollection storesInTable = new ElementCollection(By.xpath("//div[contains(@aria-label, 'stores')]//tr[contains(@class, 'level-0')]"), "Строки магазинов в таблице");
    ElementCollection addressesInTable = new ElementCollection(ByKraken.xpath("//td[1]//span[@role='img']/parent::a"), "Строки адресов в таблице");
    ElementCollection inactiveStoresInTable = new ElementCollection(By.xpath("//div[contains(@aria-label,'stores')]//span[text()='Недоступен']"), "Надписи о недоступности магазинов");
    ElementCollection activeStoresInTable = new ElementCollection(By.xpath("//div[contains(@aria-label,'stores')]//span[text()='Доступен']"), "Надписи о доступности магазинов");

    Button addNewStoreButton = new Button(By.xpath("//span[contains(text(), 'Добавить магазин')]/ancestor::button"), "Кнопка добавления нового магазина");
}
