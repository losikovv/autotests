package ru.instamart.reforged.admin.page.retailers.regions;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface RegionsElements {

    Link addNewRegionButton = new Link(By.xpath("//a[@class='button icon-plus']"), "Кнопка добавления нового региона");
    Element successCreateRegionAlert = new Element(By.xpath("//div[@class='flash success fadeOut']"), "Алерт успешного добавления нового региона");
    ElementCollection regionsNameColumn = new ElementCollection(
            By.xpath("//table[@class='index']//a[contains(@href,'/admin/operational_zones/') and not(@data-action='edit') and not(@data-action='remove')]"),
            "Список всех регионов в таблице");
    Link testRegionInRegionsTable = new Link(By.xpath("//a[text()='АвтотестГород']"), "Ссылка на добавленный тестовый регион в таблице");
    Link deleteTestRegionButton = new Link(By.xpath("//a[text()='АвтотестГород']/ancestor::tr/descendant::a[@data-action='remove']"), "Кнопка удаления у тестового региона");
    ElementCollection deleteRegionButtons = new ElementCollection(By.xpath("//tr//a[@data-action='remove']"), "Все кнопки удалить в таблице регионов");
    ElementCollection editRegionButtons = new ElementCollection(By.xpath("//tr//a[@data-action='edit']"), "Все кнопки редактировать в таблице регионов");
    ElementCollection tableRowsNumbers = new ElementCollection(By.xpath("//td[@class='align-center' and not (descendant::a)]"), "Номера регионов в таблице");
    Element pageTitle = new Element(By.xpath("//h1[@class='page-title ≈']"), "Заголовок страницы с таблицей регионов 'Список регионов'");
    Element regionsTable = new Element(By.xpath("//table[@class='index']"), "Таблица с доступными регионами");
}
