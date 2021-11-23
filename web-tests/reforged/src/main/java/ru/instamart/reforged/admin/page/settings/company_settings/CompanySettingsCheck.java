package ru.instamart.reforged.admin.page.settings.company_settings;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CompanySettingsCheck extends Check, CompanySettingsElements {

    @Step("Проверяем, что заголовок страницы отображается")
    default void checkPageTitleVisible() {
        waitAction().shouldBeVisible(pageTitle);
    }

    @Step("Проверяем, что лейбл названия компании отображается")
    default void checkCompanyNameLabelVisible() {
        waitAction().shouldBeVisible(companyNameLabel);
    }

    @Step("Проверяем, что инпут названия компании отображается")
    default void checkCompanyNameVisible() {
        waitAction().shouldBeVisible(companyName);
    }

    @Step("Проверяем, что лейбл адреса компании отображается")
    default void checkCompanyAddressLabelVisible() {
        waitAction().shouldBeVisible(companyAddressLabel);
    }

    @Step("Проверяем, что инпут адреса компании отображается")
    default void checkCompanyAddressVisible() {
        waitAction().shouldBeVisible(companyAddress);
    }

    @Step("Проверяем, что лейбл телефона компании отображается")
    default void checkCompanyPhoneLabelVisible() {
        waitAction().shouldBeVisible(companyPhoneLabel);
    }

    @Step("Проверяем, что инпут телефона компании отображается")
    default void checkCompanyPhoneVisible() {
        waitAction().shouldBeVisible(companyPhone);
    }

    @Step("Проверяем, что лейбл ИНН компании отображается")
    default void checkCompanyInnLabelVisible() {
        waitAction().shouldBeVisible(companyInnLabel);
    }

    @Step("Проверяем, что инпут ИНН компании отображается")
    default void checkCompanyInnVisible() {
        waitAction().shouldBeVisible(companyInn);
    }

    @Step("Проверяем, что лейбл КПП компании отображается")
    default void checkCompanyKppLabelVisible() {
        waitAction().shouldBeVisible(companyKppLabel);
    }

    @Step("Проверяем, что инпут КПП компании отображается")
    default void checkCompanyKppVisible() {
        waitAction().shouldBeVisible(companyKpp);
    }

    @Step("Проверяем, что лейбл БИК компании отображается")
    default void checkCompanyBikLabelVisible() {
        waitAction().shouldBeVisible(companyBikLabel);
    }

    @Step("Проверяем, что инпут БИК компании отображается")
    default void checkCompanyBikVisible() {
        waitAction().shouldBeVisible(companyBik);
    }

    @Step("Проверяем, что лейбл ОГРН компании отображается")
    default void checkCompanyOgrnLabelVisible() {
        waitAction().shouldBeVisible(companyOgrnLabel);
    }

    @Step("Проверяем, что инпут ОГРН компании отображается")
    default void checkCompanyOgrnVisible() {
        waitAction().shouldBeVisible(companyOgrn);
    }

    @Step("Проверяем, что лейбл корреспондентского счета компании отображается")
    default void checkCompanyCorrespondentAccLabelVisible() {
        waitAction().shouldBeVisible(companyCorrespondentAccLabel);
    }

    @Step("Проверяем, что инпут корреспондентского счета компании отображается")
    default void checkCompanyCorrespondentAccVisible() {
        waitAction().shouldBeVisible(companyCorrespondentAcc);
    }

    @Step("Проверяем, что лейбл расчетного счета компании отображается")
    default void checkCompanySettlementAccLabelVisible() {
        waitAction().shouldBeVisible(companySettlementAccLabel);
    }

    @Step("Проверяем, что инпут расчетного счета компании отображается")
    default void checkCompanySettlementAccVisible() {
        waitAction().shouldBeVisible(companySettlementAcc);
    }

    @Step("Проверяем, что лейбл банка компании отображается")
    default void checkCompanyBankLabelVisible() {
        waitAction().shouldBeVisible(companyBankLabel);
    }

    @Step("Проверяем, что инпут банка компании отображается")
    default void checkCompanyBankVisible() {
        waitAction().shouldBeVisible(companyBank);
    }

    @Step("Проверяем, что лейбл формы организации банка компании отображается")
    default void checkCompanyBankTypeLabelVisible() {
        waitAction().shouldBeVisible(companyBankTypeLabel);
    }

    @Step("Проверяем, что инпут формы организации банка компании отображается")
    default void checkCompanyBankTypeVisible() {
        waitAction().shouldBeVisible(companyBankType);
    }

    @Step("Проверяем, что лейбл города банка компании отображается")
    default void checkCompanyBankCityLabelVisible() {
        waitAction().shouldBeVisible(companyBankCityLabel);
    }

    @Step("Проверяем, что инпут города банка компании отображается")
    default void checkCompanyBankCityVisible() {
        waitAction().shouldBeVisible(companyBankCity);
    }

    @Step("Проверяем, что лейбл времени начала работы поддержки компании отображается")
    default void checkCompanySupportTimeStartLabelVisible() {
        waitAction().shouldBeVisible(companySupportTimeStartLabel);
    }

    @Step("Проверяем, что инпут времени начала работы поддержки компании отображается")
    default void checkCompanySupportTimeStartVisible() {
        waitAction().shouldBeVisible(companySupportTimeStart);
    }

    @Step("Проверяем, что лейбл времени конца работы поддержки компании отображается")
    default void checkCompanySupportTimeEndLabelVisible() {
        waitAction().shouldBeVisible(companySupportTimeEndLabel);
    }

    @Step("Проверяем, что инпут времени конца работы поддержки компании отображается")
    default void checkCompanySupportTimeEndVisible() {
        waitAction().shouldBeVisible(companySupportTimeEnd);
    }

    @Step("Проверяем, что лейбл руководителя компании отображается")
    default void checkCompanyHeadLabelVisible() {
        waitAction().shouldBeVisible(companyHeadLabel);
    }

    @Step("Проверяем, что инпут руководителя компании отображается")
    default void checkCompanyHeadVisible() {
        waitAction().shouldBeVisible(companyHead );
    }

    @Step("Проверяем, что лейбл доверенности руководителя компании отображается")
    default void checkCompanyHeadPoaLabelVisible() {
        waitAction().shouldBeVisible(companyHeadPoaLabel);
    }

    @Step("Проверяем, что инпут доверенности руководителя компании отображается")
    default void checkCompanyHeadPoaVisible() {
        waitAction().shouldBeVisible(companyHeadPoa);
    }

    @Step("Проверяем, что лейбл бухгалтера компании отображается")
    default void checkCompanyAccountantLabelVisible() {
        waitAction().shouldBeVisible(companyAccountantLabel);
    }

    @Step("Проверяем, что инпут бухгалтера компании отображается")
    default void checkCompanyAccountantVisible() {
        waitAction().shouldBeVisible(companyAccountant);
    }

    @Step("Проверяем, что лейбл доверенности бухгалтера компании отображается")
    default void checkCompanyAccountantPoaLabelVisible() {
        waitAction().shouldBeVisible(companyAccountantPoaLabel );
    }

    @Step("Проверяем, что инпут доверенности бухгалтера компании отображается")
    default void checkCompanyAccountantPoaVisible() {
        waitAction().shouldBeVisible(companyAccountantPoa);
    }

    @Step("Проверяем, что лейбл директора по логистике компании отображается")
    default void checkCompanyLogisticsHeadLabelVisible() {
        waitAction().shouldBeVisible(companyLogisticsHeadLabel);
    }

    @Step("Проверяем, что инпут директора по логистике компании отображается")
    default void checkcompanyLogisticsHeadVisible() {
        waitAction().shouldBeVisible(companyLogisticsHead);
    }

    @Step("Проверяем, что лейбл доверенности директора по логистике компании отображается")
    default void checkCompanyLogisticsHeadPoaLabelVisible() {
        waitAction().shouldBeVisible(companyLogisticsHeadPoaLabel);
    }

    @Step("Проверяем, что инпут доверенности директора по логистике компании отображается")
    default void checkCompanyLogisticsHeadPoaVisible() {
        waitAction().shouldBeVisible(companyLogisticsPoaHead );
    }

    @Step("Проверяем, что лейбл коммерческого директора компании отображается")
    default void checkCompanyCommercialOfficerLabelVisible() {
        waitAction().shouldBeVisible(companyCommercialOfficerLabel);
    }

    @Step("Проверяем, что инпут коммерческого директора компании отображается")
    default void checkCompanyCommercialOfficerVisible() {
        waitAction().shouldBeVisible(companyCommercialOfficer);
    }

    @Step("Проверяем, что лейбл доверенности коммерческого директора компании отображается")
    default void checkCompanyCommercialOfficerPoaLabelVisible() {
        waitAction().shouldBeVisible(companyCommercialOfficePoaLabel);
    }

    @Step("Проверяем, что инпут доверенности коммерческого директора компании отображается")
    default void checkCompanyCommercialOfficerPoaVisible() {
        waitAction().shouldBeVisible(companyCommercialOfficerPoa);
    }

    @Step("Проверяем, что кнопка сохранения изменений отображается")
    default void checkSaveChangesVisible() {
        waitAction().shouldBeVisible(saveChanges);
    }
}
