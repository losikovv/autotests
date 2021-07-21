package ru.instamart.reforged.admin.page.pages;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.core.component.Table;

public interface PagesElement {

    Element tableElement = new Element(By.xpath("//table"));
    Element tableEntry = new Element(By.id("page_11"));

    Link newPageButton = new Link(By.xpath("//a[@class='button icon-plus']"));
    Table table = new Table();
}
