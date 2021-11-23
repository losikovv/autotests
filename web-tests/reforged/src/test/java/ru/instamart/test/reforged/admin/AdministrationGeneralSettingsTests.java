package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Основные настройки")
public final class AdministrationGeneralSettingsTests extends BaseTest {

    @CaseId(346)
    @Test(description = "Корректное отображение страницы основных настроек", groups = {"acceptance", "regression"})
    public void generalSettingsPageValidate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        settings().goToPage();

        settings().checkGeneralSettingsPageHeaderVisible();
        settings().checkSeoSettingsSectionVisible();
        settings().checkDefaultSeoHeaderLabelVisible();
        settings().checkDefaultSeoHeaderVisible();
        settings().checkDefaultMetaDescriptionLabelVisible();
        settings().checkDefaultMetaDescriptionVisible();

        settings().checkEmailSettingsSectionVisible();
        settings().checkFeedbackEmailLabelVisible();
        settings().checkFeedbackEmailVisible();
        settings().checkPressEmailLabelVisible();
        settings().checkPressEmailVisible();
        settings().checkContentEmailLabelVisible();
        settings().checkContentEmailVisible();
        settings().checkB2bEmailLabelVisible();
        settings().checkB2bEmailVisible();
        settings().checkOrdersEmailLabelVisible();
        settings().checkOrdersEmailVisible();

        settings().checkSocialsSectionVisible();
        settings().checkFacebookPageLabelVisible();
        settings().checkFacebookPageVisible();
        settings().checkVkontaktePageLabelVisible();
        settings().checkVkontaktePageVisible();
        settings().checkTwitterPageLabelVisible();
        settings().checkTwitterPageVisible();
        settings().checkOkPageLabelVisible();
        settings().checkOkPageVisible();
        settings().checkInstagramPageLabelVisible();
        settings().checkInstagramPageVisible();
        settings().checkSupportTelegramLabelVisible();
        settings().checkSupportTelegramVisible();
        settings().checkSupportWhatsAppLabelVisible();
        settings().checkSupportWhatsAppVisible();

        settings().checkAboutServiceMobSectionVisible();
        settings().checkLinqFaqLabelVisible();
        settings().checkLinqFaqVisible();
        settings().checkLinkRulesLabelVisible();
        settings().checkLinkRulesVisible();
        settings().checkLinkTermsLabelVisible();
        settings().checkLinkTermsVisible();
        settings().checkPersonalPolicyLinkLabelVisible();
        settings().checkPersonalPolicyLinkVisible();
        settings().checkLinkSberprimeFaqLabelVisible();
        settings().checkLinkSberprimeFaqVisible();

        settings().checkAppReviewSectionVisible();
        settings().checkAppReviewModeLabelVisible();
        settings().checkAppReviewModeVisible();
        settings().checkAppReviewIntervalLabelVisible();
        settings().checkAppReviewIntervalVisible();
        settings().checkAppReviewIntervalCrashLabelVisible();
        settings().checkAppReviewIntervalCrashVisible();
        settings().checkAppReviewIntervalErrorLabelVisible();
        settings().checkAppReviewIntervalErrorVisible();
        settings().checkAppstoreLinkLabelVisible();
        settings().checkAppstoreLinkVisible();
        settings().checkGooglePlayLinkLabelVisible();
        settings().checkGooglePlayLinkVisible();
        settings().checkAppGalleryLinkLabelVisible();
        settings().checkAppGalleryLinkVisible();

        settings().checkCurrencySettingsSectionVisible();
        settings().checkCurrencyLabelVisible();
        settings().checkCurrencyDropDownVisible();
        settings().checkSaveButtonVisible();
        settings().checkCancelButtonVisible();
    }

    @CaseId(572)
    @Test(description = "Корректное отображение страницы настройки городов", groups = {"acceptance", "regression"})
    public void citiesSettingsPageValidate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        allCities().goToPage();
        allCities().checkPageHeaderVisible();
        allCities().checkAddCityButtonVisible();
        allCities().checkListingCitiesTableVisible();
    }

    @CaseId(575)
    @Test(description = "Корректное отображение страницы добавления городов", groups = {"acceptance", "regression"})
    public void cityAddTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        cityAdd().goToPage();
        cityAdd().checkPageTitleVisible();
        cityAdd().checkReturnButtonVisible();

        cityAdd().checkCityNameInputLabelVisible();
        cityAdd().checkCityNameInputVisible();

        cityAdd().checkCityNameInInputLabelVisible();
        cityAdd().checkCityNameInInputVisible();

        cityAdd().checkCityNameFromInputLabelVisible();
        cityAdd().checkCityNameFromInputVisible();

        cityAdd().checkCityNameToInputLabelVisible();
        cityAdd().checkcityNameToInputVisible();

        cityAdd().checkCityLockedLabelVisible();
        cityAdd().checkCityLockedVisible();

        cityAdd().checkCreateButtonVisible();
        cityAdd().checkCancelButtonVisible();

    }

    @CaseId(573)
    @Test(description = "Корректное отображение страницы редактирования городов", groups = {"acceptance", "regression"})
    public void cityEditTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        cityEdit().openAdminPage("cities/tula/edit");
        cityEdit().checkPageTitleVisible();
        cityEdit().checkReturnButtonVisible();

        cityEdit().checkCityNameInputLabelVisible();
        cityEdit().checkCityNameInputVisible();

        cityEdit().checkCityNameInInputLabelVisible();
        cityEdit().checkCityNameInInputVisible();

        cityEdit().checkCityNameFromInputLabelVisible();
        cityEdit().checkCityNameFromInputVisible();

        cityEdit().checkCityNameToInputLabelVisible();
        cityEdit().checkcityNameToInputVisible();

        cityEdit().checkCityLockedLabelVisible();
        cityEdit().checkCityLockedVisible();

        cityEdit().checkEditButtonVisible();
        cityEdit().checkCancelButtonVisible();
    }

    @CaseId(367)
    @Test(description = "Корректное отображение страницы настроек методов оплаты", groups = {"acceptance", "regression"})
    public void paymentMethodsSettingsPageValidate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        payments().goToPage();
        payments().checkPageTitleVisible();
        payments().checkPaymentMethodsTableVisible();
    }

    @CaseId(393)
    @Test(description = "Корректное отображение страницы настроек компаний", groups = {"acceptance", "regression"})
    public void companySettingsPageValidate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companySettings().goToPage();
        companySettings().checkPageTitleVisible();
        companySettings().checkCompanyNameLabelVisible();
        companySettings().checkCompanyNameVisible();
        companySettings().checkCompanyAddressLabelVisible();
        companySettings().checkCompanyAddressVisible();
        companySettings().checkCompanyPhoneLabelVisible();
        companySettings().checkCompanyPhoneVisible();
        companySettings().checkCompanyInnLabelVisible();
        companySettings().checkCompanyInnVisible();
        companySettings().checkCompanyKppLabelVisible();
        companySettings().checkCompanyKppVisible();
        companySettings().checkCompanyBikLabelVisible();
        companySettings().checkCompanyBikVisible();
        companySettings().checkCompanyOgrnLabelVisible();
        companySettings().checkCompanyOgrnVisible();
        companySettings().checkCompanyCorrespondentAccLabelVisible();
        companySettings().checkCompanyCorrespondentAccVisible();
        companySettings().checkCompanySettlementAccLabelVisible();
        companySettings().checkCompanySettlementAccVisible();
        companySettings().checkCompanyBankLabelVisible();
        companySettings().checkCompanyBankVisible();
        companySettings().checkCompanyBankTypeLabelVisible();
        companySettings().checkCompanyBankTypeVisible();
        companySettings().checkCompanyBankCityLabelVisible();
        companySettings().checkCompanyBankCityVisible();
        companySettings().checkCompanySupportTimeStartLabelVisible();
        companySettings().checkCompanySupportTimeStartVisible();
        companySettings().checkCompanySupportTimeEndLabelVisible();
        companySettings().checkCompanySupportTimeEndVisible();
        companySettings().checkCompanyHeadLabelVisible();
        companySettings().checkCompanyHeadVisible();
        companySettings().checkCompanyHeadPoaLabelVisible();
        companySettings().checkCompanyHeadPoaVisible();
        companySettings().checkCompanyAccountantLabelVisible();
        companySettings().checkCompanyAccountantVisible();
        companySettings().checkCompanyAccountantPoaLabelVisible();
        companySettings().checkCompanyAccountantPoaVisible();
        companySettings().checkCompanyLogisticsHeadLabelVisible();
        companySettings().checkcompanyLogisticsHeadVisible();
        companySettings().checkCompanyLogisticsHeadPoaLabelVisible();
        companySettings().checkCompanyLogisticsHeadPoaVisible();
        companySettings().checkCompanyCommercialOfficerLabelVisible();
        companySettings().checkCompanyCommercialOfficerVisible();
        companySettings().checkCompanyCommercialOfficerPoaLabelVisible();
        companySettings().checkCompanyCommercialOfficerPoaVisible();
        companySettings().checkSaveChangesVisible();
    }

    @CaseId(394)
    @Test(description = "Корректное отображение страницы настроек смс", groups = {"acceptance", "regression"})
    public void smsSettingsPageValidate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        smsSettings().goToPage();
        smsSettings().checkPageTitleVisible();
        smsSettings().checkSendSmsVisible();
        smsSettings().checkSendSmsLabelVisible();
        smsSettings().checkSendSmsStartVisible();
        smsSettings().checkSendSmsStartLabelVisible();
        smsSettings().checkSendSmsApologiesVisible();
        smsSettings().checkSendSmsApologiesLabelVisible();

        smsSettings().checkSendSmsTipsHeaderVisible();
        smsSettings().checkSendSmsTipsVisible();
        smsSettings().checkSendSmsTipsLabelVisible();
        smsSettings().checkStoreIdForTestVisible();
        smsSettings().checkStoreIdForTestLabelVisible();
        smsSettings().checkSendSmsTipsTemplateVisible();
        smsSettings().checkSendSmsTipsTemplateLabelVisible();

        smsSettings().checkSmsimpleHeaderVisible();
        smsSettings().checkSmsLoginVisible();
        smsSettings().checkSmsLoginLabelVisible();
        smsSettings().checkSmsPasswordVisible();
        smsSettings().checkSmsPasswordLabelVisible();
        smsSettings().checkSmsNumberVisible();
        smsSettings().checkSmsNumberLabelVisible();

        smsSettings().checkInfobipHeaderVisible();
        smsSettings().checkInfobipTokenVisible();
        smsSettings().checkInfobitTokenLabelVisible();
        smsSettings().checkInfobitNumberVisible();
        smsSettings().checkInfobitNumberLabelVisible();
        smsSettings().checkSaveButtonVisible();
    }
}