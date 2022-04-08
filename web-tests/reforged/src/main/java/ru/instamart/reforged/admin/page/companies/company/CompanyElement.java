package ru.instamart.reforged.admin.page.companies.company;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface CompanyElement {

    Input search = new Input(By.name("inn"), "поле поиска по inn");
    Button buttonSearch = new Button(By.xpath("//span[text()='Найти']"), "Кнопка 'Найти'");
    Button addCompany = new Button(By.xpath("//span[text()=' Добавить компанию']"), "кнопка добавить компанию");

    Button buttonEditCompanyName = new Button(By.xpath("//div/label[text()='Название']/../..//span[@aria-label='edit']"), "кнопка редактирования имени компании");
    Input editCompanyName = new Input(By.xpath("//input[@placeholder='Название компании']"), "поле для ввода имени компании");

    Element confirmActionModal = new Element(By.xpath("//div[@class='ant-popover-inner']"), "Всплывающее окно подтверждения действия");
    Element confirmActionModalText = new Element(By.xpath("//div[@class='ant-popover-message']"), "Сообщение во всплывающем окне подтверждения действия");
    Button confirmActionModalOk = new Button(By.xpath("//div[@class='ant-popover-buttons']/button[contains(.,'OK')]"), "Кнопка 'OK' всплывающего подтверждения");
    Button confirmActionModalCancel = new Button(By.xpath("//div[@class='ant-popover-buttons']/button[contains(.,'Отмена')]"), "Кнопка 'Отмена' всплывающего подтвержения");
    Element noticePopup = new Element(By.xpath("//div[@class='ant-message-notice']"), "Всплывающее уведомение о выполнении операции");

    //Статус
    Button confirmStatus = new Button(By.xpath("//div[./label[@title='Статус']]/following-sibling::div//button"), "Кнопка 'Подтвердить статус'");

    //Менеджер
    Input fillEmailToAddManager = new Input(By.xpath("//div[@data-qa='company_page_manager']//input"), "Поле ввода e-mail менеджера");
    ElementCollection companyManagers = new ElementCollection(By.xpath("//div[@data-qa='company_page_manager']//li[contains(@class,'ant-list-item')]"), "Менеджеры");
    ElementCollection companyManagerDeleteButtons = new ElementCollection(By.xpath("//div[@data-qa='company_page_manager']//li[contains(@class,'ant-list-item')]//button"), "Кнопки 'Удалить' (менеджера)");

    //Представители
    Input fillEmailToAddEmployee = new Input(By.xpath("//div[@data-qa='company_page_employees']//input"), "Поле ввода e-mail представителя");
    ElementCollection foundedUsers = new ElementCollection(By.xpath("//div[@class='rc-virtual-list-holder-inner']/div[contains(@class,'ant-select-item')]"), "Выпадающий список найденных пользователей");
    ElementCollection companyEmployees = new ElementCollection(By.xpath("//div[@data-qa='company_page_employees']//li[contains(@class,'ant-list-item')]"), "Представители");
    ElementCollection companyEmployeeConfirmButtons = new ElementCollection(By.xpath("(//div[@data-qa='company_page_employees']//li[contains(@class,'ant-list-item')]//button)[1]"), "Кнопки 'Подтвердить' (представителя)");
    ElementCollection companyEmployeeDeleteButtons = new ElementCollection(By.xpath("(//div[@data-qa='company_page_employees']//li[contains(@class,'ant-list-item')]//button)[2]"), "Кнопки 'Удалить' (представителя)");
}
