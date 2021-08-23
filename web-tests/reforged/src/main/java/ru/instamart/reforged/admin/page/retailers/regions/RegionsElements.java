package ru.instamart.reforged.admin.page.retailers.regions;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface RegionsElements {

    Link addNewRegionButton = new Link(By.xpath("//a[@class='button icon-plus']"), "Кнопка добавления нового региона");
    Element successCreateRegionAlert = new Element(By.xpath("//div[@class='flash success fadeOut']"), "Алерт успешного добавления нового региона");
    ElementCollection regionsColumn = new ElementCollection(
            By.xpath("//table[@class='index']//a[contains(@href,'/admin/operational_zones/') and not(@data-action='edit') and not(@data-action='remove')]"),
            "Список всех регионов в таблице");
    Link testRegionInRegionsTable = new Link(By.xpath("//a[text()='АвтотестГород']"), "Ссылка на добавленный тестовый регион в таблице");
    Link deleteTestRegionButton = new Link(By.xpath("//a[text()='АвтотестГород']/ancestor::tr/descendant::a[@data-action='remove']"), "Кнопка удаления у тестового региона");

}
