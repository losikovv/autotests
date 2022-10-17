package ru.instamart.reforged.admin.page.user_companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.UserCompaniesTable;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface UserCompaniesElement {
    Button addNewCompany = new Button(By.xpath("//button[@data-qa='users_company_documents_new_company_button']"), "Кнопка '+ Новая компания'");
    Button goBack = new Button(By.xpath("//button[@data-qa='users_company_documents_back_button']"), "Кнопка 'Назад'");

    Element listIsEmpty = new Element(By.xpath("//div[@class='ant-empty-image']"), "Плейсхолдер пустой таблицы");
    UserCompaniesTable table = new UserCompaniesTable();
}
