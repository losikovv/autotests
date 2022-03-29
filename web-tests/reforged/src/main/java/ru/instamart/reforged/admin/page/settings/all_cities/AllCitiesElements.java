package ru.instamart.reforged.admin.page.settings.all_cities;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.add_city.AddCity;
import ru.instamart.reforged.admin.block.edit_city.EditCity;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface AllCitiesElements {

    AddCity interactAddCityModal = new AddCity();
    EditCity interactEditCityModal = new EditCity();

    Element addCityAlert =  new Element(By.xpath("//div[@class='flash success']"), "Алерт добавления города");
    Element deleteCityAlert = new Element(By.xpath("//div[@class='flash success']"), "Алерт удаления города");
    Element removeCity = new Element(ByKraken.xpathExpression("//td[text()='%s']/parent::tr/descendant::a[@data-action='remove']"), "удаление города");

    Element pageHeader = new Element(By.xpath("//h4[contains(text(), 'Список городов')]"), "Заголовок страницы");
    Button addCityButton = new Button(By.xpath("//button[@class='ant-btn ant-btn-primary']"), "Кнопка 'Добавить город'");
    Element listingCitiesTable = new Element(By.xpath("//div[@class='ant-table-wrapper']"),"Таблица городов");

    Element editCityButton = new Element(ByKraken.xpathExpression("//td[text()='%s']/parent::tr//span[text()='Настройка']"),
            "Кнопка редактирования у конкретного города");
}
