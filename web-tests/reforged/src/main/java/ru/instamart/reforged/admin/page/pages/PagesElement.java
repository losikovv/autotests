package ru.instamart.reforged.admin.page.pages;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.core.component.Table;

public interface PagesElement {

    Element tableElement = new Element(By.xpath("//table"), "Таблица статических страниц");
    Element tableEntry = new Element(By.id("page_11"), "Тестовая строка таблицы статических страниц");

    Element tableEntrySpecific = new Element(ByKraken.id("page_%s"), "Конкретная строка таблицы статических страниц");
    Link tableEntrySpecificEdit = new Link(ByKraken.xpath("//a[contains(@href, '%s')]/following-sibling::a[@data-action='edit']"), "Редактирование конкретной строки таблицы статических страниц");
    Link tableEntrySpecificDelete = new Link(ByKraken.xpath("//a[contains(@href, '%s')]/following-sibling::a[@data-action='remove']"), "Удаление конкретной строки таблицы статических страниц");
    Link tableEntryRow = new Link(ByKraken.xpath("//a[@href='/%s']/ancestor::tr"), "Строка таблицы страниц");

    Link newPageButton = new Link(By.xpath("//a[@class='button icon-plus']"), "Кнопка добавления страницы");
    Table table = new Table();

    Element deleteAlert = new Element(By.xpath("//div[@class='flash success']"), "Алерт удаления");
}
