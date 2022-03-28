package ru.instamart.reforged.admin.page.settings.company_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface CompanySettingsElements {
    Element pageTitle = new Element(By.xpath("//h4"), "Заголовок страницы");

    Element companyNameLabel = new Element(By.xpath("//label[@title='Название']"), "Лейбл названия компании");
    Input companyName = new Input(By.xpath("//input[@data-qa='company_settings_company_name']"), "Инпут названия компании");

    Element companyAddressLabel = new Element(By.xpath("//label[@title='Юридический адрес']"), "Лейбл адреса компании");
    Input companyAddress = new Input(By.xpath("//input[@data-qa='company_settings_company_address']"), "Инпут адреса компании");

    Element companyPhoneLabel = new Element(By.xpath("//label[@title='Телефон']"), "Лейбл телефона компании");
    Input companyPhone = new Input(By.xpath("//input[@data-qa='company_settings_company_phone']"), "Инпут телефона компании");

    Element companyInnLabel = new Element(By.xpath("//label[@title='ИНН']"), "Лейбл ИНН компании");
    Input companyInn = new Input(By.xpath("//input[@data-qa='company_settings_company_inn']"), "Инпут ИНН компании");

    Element companyKppLabel = new Element(By.xpath("//label[@title='КПП']"), "Лейбл КПП компании");
    Input companyKpp = new Input(By.xpath("//input[@data-qa='company_settings_company_inn']"), "Инпут КПП компании");

    Element companyBikLabel = new Element(By.xpath("//label[@title='БИК']"), "Лейбл БИК компании");
    Input companyBik = new Input(By.xpath("//input[@data-qa='company_settings_company_bik']"), "Инпут БИК компании");

    Element companyOgrnLabel = new Element(By.xpath("//label[@title='ОГРН']"), "Лейбл ОГРН компании");
    Input companyOgrn = new Input(By.xpath("//input[@data-qa='company_settings_company_ogrn']"), "Инпут ОГРН компании");

    Element companyCorrespondentAccLabel = new Element(By.xpath("//label[@title='Корреспондентский счет']"), "Лейбл корреспондентского счета компании");
    Input companyCorrespondentAcc = new Input(By.xpath("//input[@data-qa='company_settings_company_correspondent_acc']"), "Инпут корреспондентского счета компании");

    Element companySettlementAccLabel = new Element(By.xpath("//label[@title='Расчетный счет']"), "Лейбл расчетного счета компании");
    Input companySettlementAcc = new Input(By.xpath("//input[@data-qa='company_settings_company_settlement_acc']"), "Инпут расчетного счета компании");

    Element companyBankLabel = new Element(By.xpath("//label[@title='Банк']"), "Лейбл банка компании");
    Input companyBank = new Input(By.xpath("//input[@data-qa='company_settings_company_bank']"), "Инпут банка компании");

    Element companyBankTypeLabel = new Element(By.xpath("//label[@title='Форма организации банка']"), "Лейбл формы организации банка компании");
    Input companyBankType = new Input(By.xpath("//input[@data-qa='company_settings_company_bank_type']"), "Инпут формы организации банка компании");

    Element companyBankCityLabel = new Element(By.xpath("//label[@title='Город банка']"), "Лейбл города банка компании");
    Input companyBankCity = new Input(By.xpath("//input[@data-qa='company_settings_company_bank_city']"), "Инпут города банка компании");

    Element companySupportTimeStartLabel = new Element(By.xpath("//label[@title='Время начала работы поддержки']"), "Лейбл времени начала работы поддержки компании");
    Input companySupportTimeStart = new Input(By.xpath("//label[@title='Время начала работы поддержки']/parent::div/following-sibling::div//input[@placeholder='Выберите время']"), "Инпут времени начала работы поддержки компании");

    Element companySupportTimeEndLabel = new Element(By.xpath("//label[@title='Время конца работы поддержки']"), "Лейбл времени конца работы поддержки компании");
    Input companySupportTimeEnd = new Input(By.xpath("//label[@title='Время конца работы поддержки']/parent::div/following-sibling::div//input[@placeholder='Выберите время']"), "Инпут времени конца работы поддержки компании");

    Element companyHeadLabel = new Element(By.xpath("//label[@title='Руководитель']"), "Лейбл руководителя компании");
    Input companyHead = new Input(By.xpath("//input[@data-qa='company_settings_company_head']"), "Инпут руководителя компании");

    Element companyHeadPoaLabel = new Element(By.xpath("//label[@title='Доверенность руководителя']"), "Лейбл доверенности руководителя компании");
    Input companyHeadPoa = new Input(By.xpath("//input[@data-qa='company_settings_company_head_poa']"), "Инпут доверенности руководителя компании");

    Element companyAccountantLabel = new Element(By.xpath("//label[@title='Бухгалтер']"), "Лейбл бухгалтера компании");
    Input companyAccountant = new Input(By.xpath("//input[@data-qa='company_settings_company_accountant']"), "Инпут бухгалтера компании");

    Element companyAccountantPoaLabel = new Element(By.xpath("//label[@title='Доверенность бухгалтера']"), "Лейбл доверенности бухгалтера компании");
    Input companyAccountantPoa = new Input(By.xpath("//input[@data-qa='company_settings_company_accountant_poa']"), "Инпут доверенности бухгалтера компании");

    Element companyLogisticsHeadLabel = new Element(By.xpath("//label[@title='Директор по логистике']"), "Лейбл директора по логистике компании");
    Input companyLogisticsHead = new Input(By.xpath("//input[@data-qa='company_settings_company_logistics_head']"), "Инпут директора по логистике компании");

    Element companyLogisticsHeadPoaLabel = new Element(By.xpath("//label[@title='Доверенность директора по логистике']"), "Лейбл доверенности директора по логистике компании");
    Input companyLogisticsPoaHead = new Input(By.xpath("//input[@data-qa='company_settings_company_logistics_head_poa']"), "Инпут доверенности директора по логистике компании");

    Element companyCommercialOfficerLabel = new Element(By.xpath("//label[@title='Коммерческий директор']"), "Лейбл коммерческого директора компании");
    Input companyCommercialOfficer = new Input(By.xpath("//input[@data-qa='company_settings_company_commercial_officer']"), "Инпут коммерческого директора компании");

    Element companyCommercialOfficePoaLabel = new Element(By.xpath("//label[@title='Доверенность коммерческого директора']"), "Лейбл доверенности коммерческого директора компании");
    Input companyCommercialOfficerPoa = new Input(By.xpath("//input[@data-qa='company_settings_company_commercial_officer_poa']"), "Инпут доверенности коммерческого директора компании");

    Button saveChanges = new Button(By.xpath("//button[@data-qa='company_settings_change_button']"), "Кнопка сохранения изменений");
}
