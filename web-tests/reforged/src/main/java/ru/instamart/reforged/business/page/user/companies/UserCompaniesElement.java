package ru.instamart.reforged.business.page.user.companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.frame.AddCompanyModal;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface UserCompaniesElement {

    AddCompanyModal addCompanyModal = new AddCompanyModal();

    Link profileEdit = new Link(By.xpath("//a[@data-qa='user_companies_profile_link']"), "Кнопка 'Личный профиль'");
    Button addCompany = new Button(By.xpath("//button[@data-qa='user_companies_add_company_button']"), "Кнопка 'Добавить компанию'");
    ElementCollection companiesList = new ElementCollection(By.xpath("//a[@data-qa='user_company_link']"), "Список добавленных компаний");
    Element emptyCompaniesListPlaceholder = new Element(By.xpath("//div[@class='ui-content-wrapper']/div[contains(.,'Добавьте компанию,')]"), "Фон пустого списка компаний");

}
