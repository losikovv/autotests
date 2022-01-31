package ru.instamart.reforged.admin.page.retailers;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface RetailersPageElements {

    Button addNewRetailerButton = new Button(By.xpath("//button[@class='ant-btn ant-btn-primary']"), "Кнопка добавления нового ретейлера");

    ElementCollection retailersInTable = new ElementCollection(By.xpath("//div[@data-qa='table_cell_retailer_logo_name']/span"), "Строки ретейлеров в таблице");
    ElementCollection retailersAccessibilityInTable = new ElementCollection(By.xpath("//tr[contains(@class, 'level-0')]//span[contains(text(), 'Доступен') or contains(text(), 'Недоступен')]"), "Записи о доступности ретейлеров в таблице");
    ElementCollection retailersCreationDateInTable = new ElementCollection(By.xpath("//td[4]"), "Даты создания ретейлеров в таблице");

    Element retailerInaccessibilityInTable = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::tr//span[text() = 'Недоступен']"), "Недоступность определенного ретейлера в таблице");
    Element retailerAccessibilityInTable = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::tr//span[text() = 'Доступен']"), "Доступность определенного ретейлера в таблице");
    Element retailerPlusIconInTable = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::td/preceding-sibling::td[contains(@class, 'icon')]"), "Кнопка плюс возле конкретного ретейлера");

    Element retailerInSearchResultTable = new Element(ByKraken.xpath("//div[@data-qa='table_cell_retailer_logo_name']/span[text()='%s']"), "Ретейлер в поисковой выдаче");
    Element retailerSearchElement = new Element(By.xpath("//div[@data-qa='retailers_list_filter']"), "Элемент поиска ретейлера для клика");
    Input retailerSearchInput = new Input(By.xpath("//div[@data-qa='retailers_list_filter']//input"), "Инпут поиска ретейлера");

    ElementCollection retailerSearchOptions = new ElementCollection(By.xpath("//div[contains(@class,'rc-virtual-list')]/div[contains(@class,'ant-select-item-option')]"), "Опции поиска ретейлеров");

    Element retailerNameOnSearchLabel = new Element(ByKraken.xpath("//span[contains(@class,'ant-select-selection-item-content')]/span[contains(text(),'%s')]"), "Надпись с выбранным ретейлером в строке поиска");
    ElementCollection storesInTable = new ElementCollection(By.xpath("//td//span[@class='ant-btn-link']"), "Строки магазинов в таблице");
    ElementCollection addressDatesInTable = new ElementCollection(By.xpath("//tr[@class='ant-table-row ant-table-row-level-1']//td[3]"), "Коллекция элементов дат создания адресов магазинов");

    ElementCollection addressesInTable = new ElementCollection(By.xpath("//span[contains(@class,'anticon-right')]/preceding-sibling::span"), "Коллекция элементов адресов магазинов");
    Element sortRetailersViaNameInTable = new Element(By.xpath("//span[text()='Название']/ancestor::div[@class='ant-table-column-sorters-with-tooltip']"), "Кнопка сортировки по имени ретейлеров в таблице");
    Element sortRetailersViaCreationDateInTable = new Element(By.xpath("//span[text()='Дата создания']/ancestor::div[@class='ant-table-column-sorters-with-tooltip']"), "Кнопка сортировки по дате создания ретейлеров в таблице");

    Element sortRetailersViaNameAscInTable = new Element(By.xpath("//span[text()='Название']/ancestor::div[@class='ant-table-column-sorters']//span[contains(@class, 'sorter-up active')]"), "Иконка сортировки по имени ретейлеров в таблице ASC");
    Element sortRetailersViaNameDescInTable = new Element(By.xpath("//span[text()='Название']/ancestor::div[@class='ant-table-column-sorters']//span[contains(@class, 'sorter-down active')]"), "Иконка сортировки по имени ретейлеров в таблице DESC");

    Element sortRetailersViaCreationDateAsc = new Element(By.xpath("//span[text()='Дата создания']/ancestor::div[@class='ant-table-column-sorters']//span[contains(@class, 'sorter-up active')]"), "Иконка сортировки по дате создания ретейлеров в таблице ASC");
    Element sortRetailersViaCreationDateDesc = new Element(By.xpath("//span[text()='Дата создания']/ancestor::div[@class='ant-table-column-sorters']//span[contains(@class, 'sorter-down active')]"), "Иконка сортировки по дате создания ретейлеров в таблице DESC");

    Element spinner = new Element(By.xpath("//div[@class='ant-spin ant-spin-spinning']"), "Спиннер");

    Element addNewStoreForRetailer = new Element(ByKraken.xpath("//span[text()='%s']/ancestor::tr[contains(@class,'level-0')]//a[contains(@href, 'new')]"), "Кнопка 'Добавить магазин' у определенного ретейлера");
}
