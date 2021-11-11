package ru.instamart.reforged.admin.page.companies.new_companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface NewCompaniesElement {

    Input inn = new Input(By.xpath("//input[@label='ИНН']"), "поле для ввода ИНН");
    Input companyName = new Input(By.name("name"), "полу для ввода названия компании");
    Input ownerEmail = new Input(By.name("ownerEmail"), "полу для ввода почты владельца компании");
    Button submit = new Button(By.xpath("//button[@type='submit']/span[text()='Создать']"), "кнопка создания новой компании");
}
