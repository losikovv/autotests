package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_zone_edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface StoreZoneElements {

    Element zonePageTitle = new Element(ByKraken.xpathExpression("//span[contains(text(), 'Редактирование зон магазина %s')]"), "Заголовок страницы редактирования зоны");
    Element zonePage = new Element(By.xpath("//div[@data-qa='store_zones_page']"), "Страница редактирования зоны");
    Element zonesTable = new Element(By.xpath("//div[@class='ant-table-wrapper']"), "Таблица зон");
    Element hint = new Element(By.xpath("//p[contains(text(), 'перенастроить зоны')]"), "Подсказка о таблице зон");
    Element backButton = new Element(By.xpath("//div[@class='ant-page-header-heading']//button"), "Кнопка 'назад' на странице зон");
    Element downloadButton = new Element(By.xpath("//span[@aria-label='upload']/ancestor::button"), "Кнопка 'загрузить' на странице зон");

    ElementCollection zoneInTable = new ElementCollection(By.xpath("//tr[contains(@class, 'level-0')]"), "Зоны в таблице");
    ElementCollection deleteZoneInTableButton = new ElementCollection(By.xpath("//tr[contains(@class, 'level-0')]//button"), "Кнопки удаления зон в таблице");
}
