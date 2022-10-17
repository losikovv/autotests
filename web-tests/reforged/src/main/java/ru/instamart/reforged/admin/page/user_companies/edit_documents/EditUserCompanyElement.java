package ru.instamart.reforged.admin.page.user_companies.edit_documents;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditUserCompanyElement {
    Button backToCompaniesList = new Button(By.xpath("//button[@data-qa='users_company_documents_edit_company_back_button']"), "Кнопка 'Назад к списку компаний'");

    Input inn = new Input(By.xpath("//input[@data-qa='users_company_documents_form_inn_input']"), "Поле ввода 'ИНН'");
    Element innError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_inn_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'ИНН'");
    Input juridicalName = new Input(By.xpath("//input[@data-qa='users_company_documents_form_name_input']"), "Поле ввода 'Юридическое лицо покупателя'");
    Element juridicalNameError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_name_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Юридическое лицо покупателя'");
    Input juridicalAddress = new Input(By.xpath("//textarea[@data-qa='users_company_documents_form_address_input']"), "Поле ввода 'Юридический адрес покупателя'");
    Element juridicalAddressError = new Element(By.xpath("//textarea[@data-qa='users_company_documents_form_address_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Юридический адрес покупателя'");
    Input kpp = new Input(By.xpath("//input[@data-qa='users_company_documents_form_kpp_input']"), "Поле ввода 'КПП'");
    Element kppError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_kpp_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'КПП'");
    Input consigneeName = new Input(By.xpath("//input[@data-qa='users_company_documents_form_consignee_name_input']"), "Поле ввода 'Юридическое лицо грузополучателя'");
    Element consigneeNameError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_consignee_name_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Юридическое лицо грузополучателя'");
    Input consigneeAddress = new Input(By.xpath("//textarea[@data-qa='users_company_documents_form_consignee_address_input']"), "Поле ввода 'Юридический адрес грузополучателя'");
    Element consigneeAddressError = new Element(By.xpath("//textarea[@data-qa='users_company_documents_form_consignee_address_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Юридический адрес грузополучателя'");
    Input consigneeKpp = new Input(By.xpath("//input[@data-qa='users_company_documents_form_consignee_kpp_input']"), "Поле ввода 'КПП грузополучателя'");
    Element consigneeKppError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_consignee_kpp_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'КПП грузополучателя'");
    Input bik = new Input(By.xpath("//input[@data-qa='users_company_documents_form_bik_input']"), "Поле ввода 'БИК'");
    Element bikError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_bik_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'БИК'");
    Input correspondentAccount = new Input(By.xpath("//input[@data-qa='users_company_documents_form_correspondent_account_input']"), "Поле ввода 'Корреспондентский счет'");
    Element correspondentAccountError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_correspondent_account_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Корреспондентский счет'");
    Input operationalAccount = new Input(By.xpath("//input[@data-qa='users_company_documents_form_operating_account_input']"), "Поле ввода 'Расчётный счёт'");
    Element operationalAccountError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_operating_account_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Расчётный счёт'");
    Input bank = new Input(By.xpath("//input[@data-qa='users_company_documents_form_bank_input']"), "Поле ввода 'Банк'");
    Element bankError = new Element(By.xpath("//input[@data-qa='users_company_documents_form_bank_input']/../../..//div[@class='ant-form-item-explain-error']"), "Ошибка в поле ввода 'Банк'");

    Button submit = new Button(By.xpath("//button[@data-qa='users_company_documents_form_submit']"), "Кнопка 'Изменить'");
    Button cancel = new Button(By.xpath("//button[@data-qa='users_company_documents_form_cancel']"), "Кнопка 'Отменить'");
}
