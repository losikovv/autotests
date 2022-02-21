package ru.instamart.reforged.business.page.user.companies.companyInfo;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface CompanyInfoElement {

    Header header = new Header();

    Element navigationBlock = new Element(By.xpath("//div[@class='ui-content-wrapper']//nav"), "Меню страницы 'Компания'");

    Button addCompany = new Button(By.xpath("//button[@data-qa='user_companies_add_company_button']"), "Кнопка 'Добавить компанию'");
    Button leaveCompany = new Button(By.xpath("//button[@data-qa='user_companies_leave_company_button']"), "Кнопка 'Добавить компанию'");

    Button refreshAccountInfo = new Button(By.xpath("//div[contains(.,'Ваш счёт')]/button"), "Кнопка обновления информации по счёту");
    Element accountUpdateInfo = new Element(By.xpath("//button//div[contains(.,'Последнее обновление')]"), "Всплывающая подсказка 'Последнее обновление...'");
    Element accountAbout = new Element(By.xpath("//span[contains(.,'Баланс может обновляться')]"), "Иконка с вопросительным знаком в блоке состояния счёта");
    Element accountUpdateWarning = new Element(By.xpath("//span//div[contains(.,'Баланс может')]"), "Всплывающая подсказка 'Баланс может обновляться с задержкой...'");
    Element paymentAccountAmount = new Element(By.xpath("//div[contains(.,'Ваш счёт')][./button]/../following-sibling::div"), "Сумма на счёте");

    Element companyInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[1]"), "Блок основной информации о компании");
    Element companyUserInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[2]"), "Блок информации о представителях компании");
    Element companySecurityInfo = new Element(By.xpath("//div[./button[@data-qa='user_companies_add_company_button']]/following-sibling::div[3]"), "Блок информации 'Код безопасности'");

    Element managerInfo = new Element(By.xpath("//div[@class='ui-content-wrapper']//nav/following-sibling::div[contains(.,'Ваш персональный менеджер')]"), "Блок 'Ваш персональный менеджер'");


    Button goForward = new Button(By.xpath("//a[@class='ui-pagination__link'][contains(.,'Вперед')]"), "Кнопка 'Вперёд'");
    Button changeSecurityCode = new Button(By.xpath("//button[@data-qa='company_account_change__security_code_button']"), "Кнопка 'Изменить' (код безопасности)");
}
