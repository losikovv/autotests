package ru.instamart.reforged.admin.page.pages;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.PagesTable;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface PagesElement {

    Element tableElement = new Element(By.xpath("//table"), "Таблица статических страниц");
    Element tableEntry = new Element(By.xpath("//tr[@data-row-key='15']"), "Тестовая строка таблицы статических страниц");

    Link newPageButton = new Link(By.xpath("//button[@data-qa='pages_create_page_goto']"), "Кнопка добавления страницы");
    PagesTable tableComponent = new PagesTable();

    Element deleteAlert = new Element(By.xpath("//div[contains(@class,'ant-message-success')]"), "Алерт удаления");
    Element deletePopup = new Element(By.xpath("//div[contains(@class,'popconfirm')]//div[contains(text(),'Вы уверены?')]"), "Попап удаления");
    Button deletePopupConfirm = new Button(By.xpath("//div[contains(@class,'popconfirm')]//button[contains(@class,'primary')]"), "Кнопка подтверждения на попапе удаления");
}
