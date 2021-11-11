package ru.instamart.reforged.admin.page.companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Table;

public interface CompaniesElement {

    Input search = new Input(By.name("inn"), "поле поиска по inn");
    Button buttonSearch = new Button(By.xpath("//span[text()='Найти']"), "поле поиска по inn");
    Button addCompany = new Button(By.xpath("//span[text()=' Добавить компанию']"), "кнопка добавить компанию");
    Table companyTable = new Table();

    Button buttonEditCompanyName = new Button(By.xpath("//div/label[text()='Название']/../..//span[@aria-label='edit']"), "кнопка редактирования имени компании");
    Input editCompanyName = new Input(By.xpath("//input[@placeholder='Название компании']"), "поле для ввода имени компании");
}
