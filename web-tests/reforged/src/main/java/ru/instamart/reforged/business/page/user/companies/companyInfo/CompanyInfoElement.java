package ru.instamart.reforged.business.page.user.companies.companyInfo;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface CompanyInfoElement {

    Element navigationBlock = new Element(By.xpath("//div[@class='ui-content-wrapper']//nav"), "Меню страницы 'Компания'");

    Button addCompany = new Button(By.xpath("//button[@data-qa='user_companies_add_company_button']"), "Кнопка 'Добавить компанию'");
    Button leaveCompany = new Button(By.xpath("//button[@data-qa='user_companies_leave_company_button']"), "Кнопка 'Добавить компанию'");

    Element companyInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[1]"), "Блок основной информации о компании");
    Element companyUserInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[2]"), "Блок информации о представителях компании");
    Element companySecurityInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[3]"), "Блок информации 'Код безопасности'");

    Element managerInfo = new Element(By.xpath("//div[@class='ui-content-wrapper']//nav/following-sibling::div[contains(.,'Ваш персональный менеджер')]"), "Блок 'Ваш персональный менеджер'");

    Button changeSecurityCode = new Button(By.xpath("//button[@data-qa='company_account_change__security_code_button']"), "Кнопка 'Изменить' (код безопасности)");
}
