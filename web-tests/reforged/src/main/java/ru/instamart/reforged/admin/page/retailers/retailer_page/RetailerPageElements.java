package ru.instamart.reforged.admin.page.retailers.retailer_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.retailers.retailer_page.appearance_sidebar.AppearanceSidebar;
import ru.instamart.reforged.admin.page.retailers.retailer_page.settings_sidebar.SettingsSidebar;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RetailerPageElements {

    SettingsSidebar settingsSidebar = new SettingsSidebar();
    AppearanceSidebar appearanceSidebar = new AppearanceSidebar();

    Element retailerName = new Element(By.xpath("//span[contains(.,'Ритейлер')]/following-sibling::span"), "Свойства ритейлера - Наименование");
    Element retailerUrl = new Element(By.xpath("//span[contains(.,'Название ритейлера в url')]/following-sibling::span"), "Свойства ритейлера - Название ритейлера в url");
    Element categoriesDepth = new Element(By.xpath("//span[contains(.,'Глубина вложения категорий на главной')]/following-sibling::span"), "Свойства ритейлера - Глубина вложения категорий на главной");
    Element importKey = new Element(By.xpath("//span[contains(.,'Ключ в файле импорта')]/following-sibling::span"), "Свойства ритейлера - Ключ в файле импорта");
    Element juridicalName = new Element(By.xpath("//span[contains(.,'Юридическое имя')]/following-sibling::span"), "Свойства ритейлера - Юридическое имя");
    Element inn = new Element(By.xpath("//span[contains(.,'Инн')]/following-sibling::span"), "Свойства ритейлера - Инн");
    Element phone = new Element(By.xpath("//span[contains(.,'Телефон')]/following-sibling::span"), "Свойства ритейлера - Телефон");
    Element juridicalAddress = new Element(By.xpath("//span[contains(.,'Юридический адрес')]/following-sibling::span"), "Свойства ритейлера - Юридический адрес");
    Element contractType = new Element(By.xpath("//span[contains(.,'Тип договора')]/following-sibling::span"), "Свойства ритейлера - Тип договора");

    ElementCollection storesInTable = new ElementCollection(By.xpath("//div[contains(@aria-label, 'stores')]//tr[contains(@class, 'level-0')]"), "Строки магазинов в таблице");
    ElementCollection addressesInTable = new ElementCollection(ByKraken.xpathExpression("//td[1]//span[@role='img']/parent::a"), "Строки адресов в таблице");
    ElementCollection inactiveStoresInTable = new ElementCollection(By.xpath("//div[contains(@aria-label,'stores')]//span[text()='Недоступен']"), "Надписи о недоступности магазинов");
    ElementCollection activeStoresInTable = new ElementCollection(By.xpath("//div[contains(@aria-label,'stores')]//span[text()='Доступен']"), "Надписи о доступности магазинов");

    Button addNewStoreButton = new Button(By.xpath("//span[contains(text(), 'Добавить магазин')]/ancestor::button"), "Кнопка добавления нового магазина");
    Element addressInTableWithText = new Element(ByKraken.xpath("//a[contains(@href,'stores')]//span[text()='%s ']"), "Элемент адреса магазина с определенным текстом");
    Element addressInTable = new Element(ByKraken.xpath("//a[contains(@href,'stores')]//span[@role='img']/preceding-sibling::span"), "Первый адрес магазина");

    Button retailerSettings = new Button(By.xpath("//button[contains(.,'Настройки ритейлера')]"), "Кнопка 'Настройки ритейлера'");
    Button retailerAppearance = new Button(By.xpath("//button[contains(.,'Внешний вид')]"), "Кнопка 'Внешний вид'");
}
