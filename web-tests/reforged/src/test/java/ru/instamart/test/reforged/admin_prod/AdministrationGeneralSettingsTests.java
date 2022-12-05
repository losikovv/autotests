package ru.instamart.test.reforged.admin_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.PROD_ADMIN_SMOKE;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Основные настройки")
public final class AdministrationGeneralSettingsTests {

    @CaseId(346)
    @Test(description = "Корректное отображение страницы основных настроек", groups =  PROD_ADMIN_SMOKE)
    public void generalSettingsPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

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

        settings().checkMobileAppVersionSectionVisible();
        settings().checkMinimalIosVersionLabelVisible();
        settings().checkMinimalIosVersionVisible();
        settings().checkRecommendIosVersionLabelVisible();
        settings().checkRecommendIosVersionVisible();
        settings().checkMinimalAndroidVersionLabelVisible();
        settings().checkMinimalAndroidVersionVisible();
        settings().checkRecommendAndroidVersionLabelVisible();
        settings().checkRecommendAndroidVersionVisible();

        settings().checkMobileAnalyticsSectionVisible();
        settings().checkAnalyticsUrlIosLabelVisible();
        settings().checkAnalyticsUrlIosVisible();
        settings().checkAnalyticsKeyIosLabelVisible();
        settings().checkAnalyticsKeyIosVisible();
        settings().checkAnalyticsUrlAndroidLabelVisible();
        settings().checkAnalyticsUrlAndroidVisible();
        settings().checkAnalyticsKeyAndroidLabelVisible();
        settings().checkAnalyticsKeyAndroidVisible();

        settings().checkMobileAppDomenWhitelistSectionVisible();
        settings().checkDomensLabelVisible();
        settings().checkDomensVisible();

        settings().checkFraudModerationSectionVisible();
        settings().checkModerationLimitLabelVisible();
        settings().checkModerationLimitVisible();

        settings().checkCurrencySettingsSectionVisible();
        settings().checkCurrencyLabelVisible();
        settings().checkCurrencyDropDownVisible();
        settings().checkSaveButtonVisible();
        settings().checkCancelButtonVisible();
    }

    @CaseId(572)
    @Test(description = "Корректное отображение страницы настройки городов", groups = PROD_ADMIN_SMOKE)
    public void citiesSettingsPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        allCities().goToPage();
        allCities().checkPageHeaderVisible();
        allCities().checkAddCityButtonVisible();
        allCities().checkListingCitiesTableVisible();
    }

    @CaseId(575)
    @Test(description = "Корректное отображение страницы добавления городов", groups = PROD_ADMIN_SMOKE)
    public void cityAddPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        allCities().goToPage();
        allCities().clickOnAddCityButton();

        allCities().interactAddCityModal().checkModalOpen();
        allCities().interactAddCityModal().checkModalTitleVisible();
        allCities().interactAddCityModal().checkCloseButtonVisible();

        allCities().interactAddCityModal().checkCityNameInputLabelVisible();
        allCities().interactAddCityModal().checkCityNameInputVisible();

        allCities().interactAddCityModal().checkCityNameInInputLabelVisible();
        allCities().interactAddCityModal().checkCityNameInInputVisible();

        allCities().interactAddCityModal().checkCityNameFromInputLabelVisible();
        allCities().interactAddCityModal().checkCityNameFromInputVisible();

        allCities().interactAddCityModal().checkCityNameToInputLabelVisible();
        allCities().interactAddCityModal().checkCityNameToInputVisible();

        allCities().interactAddCityModal().checkCityLinkLabelVisible();
        allCities().interactAddCityModal().checkCityLinkInputVisible();

        allCities().interactAddCityModal().checkCreateButtonVisible();
        allCities().interactAddCityModal().checkCancelButtonVisible();

    }

    @CaseId(573)
    @Test(description = "Корректное отображение страницы редактирования городов", groups = PROD_ADMIN_SMOKE)
    public void cityEditPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        allCities().goToPage();
        allCities().clickOnEditCityButton("Абакан");

        allCities().interactEditCityModal().checkModalOpen();
        allCities().interactEditCityModal().checkModalTitleVisible();
        allCities().interactEditCityModal().checkCloseButtonVisible();

        allCities().interactEditCityModal().checkCityNameInputLabelVisible();
        allCities().interactEditCityModal().checkCityNameInputVisible();

        allCities().interactEditCityModal().checkCityNameInInputLabelVisible();
        allCities().interactEditCityModal().checkCityNameInInputVisible();

        allCities().interactEditCityModal().checkCityNameFromInputLabelVisible();
        allCities().interactEditCityModal().checkCityNameFromInputVisible();

        allCities().interactEditCityModal().checkCityNameToInputLabelVisible();
        allCities().interactEditCityModal().checkCityNameToInputVisible();

        allCities().interactEditCityModal().checkCityLinkLabelVisible();
        allCities().interactEditCityModal().checkCityLinkInputVisible();

        allCities().interactEditCityModal().checkCityLockedButtonVisible();

        allCities().interactEditCityModal().checkCreateButtonVisible();
        allCities().interactEditCityModal().checkCancelButtonVisible();
    }

    @CaseId(367)
    @Test(description = "Корректное отображение страницы настроек методов оплаты", groups = PROD_ADMIN_SMOKE)
    public void paymentMethodsSettingsPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        payments().goToPage();
        payments().checkPageTitleVisible();
        payments().checkPaymentMethodsTableVisible();
    }

    @CaseId(393)
    @Test(description = "Корректное отображение страницы настроек компаний", groups = PROD_ADMIN_SMOKE)
    public void companySettingsPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

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
    @Test(description = "Корректное отображение страницы настроек смс", groups = PROD_ADMIN_SMOKE)
    public void smsSettingsPageValidate() {
        UserData defaultAdmin = UserManager.getDefaultAdmin();

        login().goToPage();
        login().clickOnLoginViaActiveDirectory();

        activeDirectory().fillMail(defaultAdmin.getEmail());
        activeDirectory().fillPassword(defaultAdmin.getPassword());
        activeDirectory().clickOnLoginButton();

        main().interactAuthoredHeader().checkAdminAuth();

        smsSettings().goToPageOld();
        smsSettings().checkPageTitleVisible();
        smsSettings().checkSendSmsVisible();
        smsSettings().checkSendSmsStartVisible();
        smsSettings().checkSendSmsApologiesVisible();

        smsSettings().checkSendSmsTipsHeaderVisible();
        smsSettings().checkSendSmsTipsVisible();
        smsSettings().checkStoreIdForTestVisible();
        smsSettings().checkSendSmsTipsTemplateVisible();

        smsSettings().checkSmsimpleHeaderVisible();
        smsSettings().checkSmsLoginVisible();
        smsSettings().checkSmsPasswordVisible();
        smsSettings().checkSmsNumberVisible();

        smsSettings().checkInfobipHeaderVisible();
        smsSettings().checkInfobipTokenVisible();
        smsSettings().checkInfobitNumberVisible();
        smsSettings().checkSaveButtonVisible();
    }
}