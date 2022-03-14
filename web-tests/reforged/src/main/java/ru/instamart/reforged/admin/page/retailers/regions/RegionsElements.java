package ru.instamart.reforged.admin.page.retailers.regions;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.retailers.regions.add_new.RegionsAdd;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface RegionsElements {

    RegionsAdd regionsAdd = new RegionsAdd();

    Button addNewRegionButton = new Button(By.xpath("//button[@class='ant-btn ant-btn-primary']"), "Кнопка добавления нового региона");
    Element successCreateRegionAlert = new Element(By.xpath("//div[@class='flash success fadeOut']"), "Алерт успешного добавления нового региона");
    ElementCollection regionsNameColumn = new ElementCollection(By.xpath("//td[2]/p"), "Список всех регионов в таблице");
    ElementCollection tableRowsNumbers = new ElementCollection(By.xpath("//td[1]/p"), "Номера регионов в таблице");
    Element pageTitle = new Element(By.xpath("//h4[@class='ant-typography']"), "Заголовок страницы с таблицей регионов 'Список регионов'");
    Element regionsTable = new Element(By.xpath("//div[@class='ant-table-wrapper']"), "Таблица с доступными регионами");
    Element removeRegion = new Element(ByKraken.xpathExpression("//a[text()='%s']/ancestor::tr/descendant::a[@data-action='remove']"), "удаление региона");
    Element city = new Element(ByKraken.xpathExpression("//p[text()='%s']"), "тестовый город в списке городов");

    ElementCollection regionSettingsButtons = new ElementCollection(By.xpath("//div[@class='ant-table-wrapper']//span[contains(text(), 'Настройка региона')]"), "Все кнопки настроек региона в таблице регионов");
    ElementCollection dispatchSettingsButtons = new ElementCollection(By.xpath("//div[@class='ant-table-wrapper']//span[contains(text(), 'Настройка диспетчеризации')]"), "Все кнопки настроек диспетчеризации в таблице регионов");

    Input regionSearch = new Input(By.xpath("//input[@data-qa='operational_zones_list_filter']"), "Инпут поиска регионов");
}
