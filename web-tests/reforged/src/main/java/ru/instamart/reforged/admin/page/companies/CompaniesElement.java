package ru.instamart.reforged.admin.page.companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.CompaniesTable;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface CompaniesElement {

    Input search = new Input(By.name("inn"), "поле поиска по inn");
    Button buttonSearch = new Button(By.xpath("//span[text()='Найти']"), "Кнопка 'Найти'");
    Button addCompany = new Button(By.xpath("//span[text()=' Добавить компанию']"), "кнопка добавить компанию");

    Element companiesTable = new Element(By.xpath("//table/tbody"), "Записи в таблице");
    CompaniesTable companies = new CompaniesTable();
}
