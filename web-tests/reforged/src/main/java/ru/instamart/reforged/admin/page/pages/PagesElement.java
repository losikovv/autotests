package ru.instamart.reforged.admin.page.pages;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.PagesTable;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface PagesElement {

    Element tableElement = new Element(By.xpath("//table"), "Таблица статических страниц");
    Element tableEntry = new Element(By.id("page_11"), "Тестовая строка таблицы статических страниц");

    Link newPageButton = new Link(By.xpath("//a[@class='button icon-plus']"), "Кнопка добавления страницы");
    PagesTable tableComponent = new PagesTable();

    Element deleteAlert = new Element(By.xpath("//div[@class='flash success']"), "Алерт удаления");
}
