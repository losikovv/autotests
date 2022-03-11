package ru.instamart.reforged.admin.page.settings.all_cities;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface AllCitiesElements {

    Element addCityAlert =  new Element(By.xpath("//div[@class='flash success']"), "Алерт добавления города");
    Element deleteCityAlert = new Element(By.xpath("//div[@class='flash success']"), "Алерт удаления города");
    Element removeCity = new Element(ByKraken.xpathExpression("//td[text() = '%s']/parent::tr/descendant::a[@data-action='remove']"), "удаление города");

    Element pageHeader = new Element(By.xpath("//h1[contains(@class, 'page-title')]"), "Заголовок страницы");
    Link addCityButton = new Link(By.xpath("//a[@class='button icon-plus']"), "Кнопка 'Добавить город'");
    Element listingCitiesTable = new Element(By.id("listing_cities"),"Таблица городов");
}
