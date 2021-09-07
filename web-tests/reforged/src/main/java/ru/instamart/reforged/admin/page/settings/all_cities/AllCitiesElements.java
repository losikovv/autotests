package ru.instamart.reforged.admin.page.settings.all_cities;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface AllCitiesElements {

    Element addCityAlert =  new Element(By.xpath("//div[@class='flash success']"), "Алерт добавления города");
    Element deleteCityAlert = new Element(By.xpath("//div[@class='flash success']"), "Алерт удаления города");
}
