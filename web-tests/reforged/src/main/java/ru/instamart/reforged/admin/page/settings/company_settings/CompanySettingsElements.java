package ru.instamart.reforged.admin.page.settings.company_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface CompanySettingsElements {
    Element pageTitle = new Element(By.xpath("//h1[@class='page-title ≈']"), "Заголовок страницы");

    Element companyNameLabel = new Element(By.xpath("//label[@for='company_name']"), "Лейбл названия компании");
    Input companyName = new Input(By.id("company_name"), "Инпут названия компании");

    Element companyAddressLabel = new Element(By.xpath("//label[@for='company_address']"), "Лейбл адреса компании");
    Input companyAddress = new Input(By.id("company_address"), "Инпут адреса компании");

    Element companyPhoneLabel = new Element(By.xpath("//label[@for='company_phone']"), "Лейбл телефона компании");
    Input companyPhone = new Input(By.id("company_phone"), "Инпут телефона компании");

    Element companyInnLabel = new Element(By.xpath("//label[@for='company_inn']"), "Лейбл ИНН компании");
    Input companyInn = new Input(By.id("company_inn"), "Инпут ИНН компании");

    Element companyKppLabel = new Element(By.xpath("//label[@for='company_kpp']"), "Лейбл КПП компании");
    Input companyKpp = new Input(By.id("company_kpp"), "Инпут КПП компании");

    Element companyBikLabel = new Element(By.xpath("//label[@for='company_bik']"), "Лейбл БИК компании");
    Input companyBik = new Input(By.id("company_bik"), "Инпут БИК компании");

    Element companyOgrnLabel = new Element(By.xpath("//label[@for='company_ogrn']"), "Лейбл ОГРН компании");
    Input companyOgrn = new Input(By.id("company_ogrn"), "Инпут ОГРН компании");

    Element companyCorrespondentAccLabel = new Element(By.xpath("//label[@for='company_correspondent_acc']"), "Лейбл корреспондентского счета компании");
    Input companyCorrespondentAcc = new Input(By.id("company_correspondent_acc"), "Инпут корреспондентского счета компании");

    Element companySettlementAccLabel = new Element(By.xpath("//label[@for='company_settlement_acc']"), "Лейбл расчетного счета компании");
    Input companySettlementAcc = new Input(By.id("company_settlement_acc"), "Инпут расчетного счета компании");

    Element companyBankLabel = new Element(By.xpath("//label[@for='company_bank']"), "Лейбл банка компании");
    Input companyBank = new Input(By.id("company_bank"), "Инпут банка компании");

    Element companyBankTypeLabel = new Element(By.xpath("//label[@for='company_bank_type']"), "Лейбл формы организации банка компании");
    Input companyBankType = new Input(By.id("company_bank_type"), "Инпут формы организации банка компании");

    Element companyBankCityLabel = new Element(By.xpath("//label[@for='company_bank_city']"), "Лейбл города банка компании");
    Input companyBankCity = new Input(By.id("company_bank_city"), "Инпут города банка компании");

    Element companySupportTimeStartLabel = new Element(By.xpath("//label[@for='company_support_time_start']"), "Лейбл времени начала работы поддержки компании");
    Input companySupportTimeStart = new Input(By.id("company_support_time_start"), "Инпут времени начала работы поддержки компании");

    Element companySupportTimeEndLabel = new Element(By.xpath("//label[@for='company_support_time_end']"), "Лейбл времени конца работы поддержки компании");
    Input companySupportTimeEnd = new Input(By.id("company_support_time_end"), "Инпут времени конца работы поддержки компании");

    Element companyHeadLabel = new Element(By.xpath("//label[@for='company_head']"), "Лейбл руководителя компании");
    Input companyHead = new Input(By.id("company_head"), "Инпут руководителя компании");

    Element companyHeadPoaLabel = new Element(By.xpath("//label[@for='company_head_poa']"), "Лейбл доверенности руководителя компании");
    Input companyHeadPoa = new Input(By.id("company_head_poa"), "Инпут доверенности руководителя компании");

    Element companyAccountantLabel = new Element(By.xpath("//label[@for='company_accountant']"), "Лейбл бухгалтера компании");
    Input companyAccountant = new Input(By.id("company_accountant"), "Инпут бухгалтера компании");

    Element companyAccountantPoaLabel = new Element(By.xpath("//label[@for='company_accountant']"), "Лейбл доверенности бухгалтера компании");
    Input companyAccountantPoa = new Input(By.id("company_accountant"), "Инпут доверенности бухгалтера компании");

    Element companyLogisticsHeadLabel = new Element(By.xpath("//label[@for='company_head']"), "Лейбл директора по логистике компании");
    Input companyLogisticsHead = new Input(By.id("company_logistics_head"), "Инпут директора по логистике компании");

    Element companyLogisticsHeadPoaLabel = new Element(By.xpath("//label[@for='company_logistics_head_poa']"), "Лейбл доверенности директора по логистике компании");
    Input companyLogisticsPoaHead = new Input(By.id("company_logistics_head_poa"), "Инпут доверенности директора по логистике компании");

    Element companyCommercialOfficerLabel = new Element(By.xpath("//label[@for='company_commercial_officer']"), "Лейбл коммерческого директора компании");
    Input companyCommercialOfficer = new Input(By.id("company_commercial_officer"), "Инпут коммерческого директора компании");

    Element companyCommercialOfficePoaLabel = new Element(By.xpath("//label[@for='company_commercial_officer_poa']"), "Лейбл доверенности коммерческого директора компании");
    Input companyCommercialOfficerPoa = new Input(By.id("company_commercial_officer_poa"), "Инпут доверенности коммерческого директора компании");

    Button saveChanges = new Button(By.xpath("//button[@type='submit']"), "Кнопка сохранения изменений");
}
