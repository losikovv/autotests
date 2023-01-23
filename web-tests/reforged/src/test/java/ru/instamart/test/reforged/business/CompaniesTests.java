package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.CookieProvider;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.Group.SMOKE_B2B;
import static ru.instamart.reforged.business.page.BusinessRouter.*;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;

@Epic("SMBUSINESS UI")
@Feature("Раздел 'Компании' B2C и B2B")
public final class CompaniesTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("21")
    @Test(description = "Переход в раздел 'Компании'", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void gotoCompaniesFromHeader() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsEmpty();
        companies().checkProfileButtonVisible();
        companies().checkAddCompanyButtonVisible();
    }

    @TmsLink("750")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Переход в раздел 'Компании' (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void gotoCompaniesFromHeaderB2C() {
        var user = UserManager.getQaUser();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(user);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToProfile();
        b2cShop().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        b2cShop().interactHeader().interactAccountMenu().clickToCompanies();

        b2cCompanies().checkCompaniesListIsEmpty();
        b2cCompanies().checkProfileButtonVisible();
        b2cCompanies().checkAddCompanyButtonVisible();
    }

    @TmsLink("22")
    @Test(description = "Переход в профиль из раздела 'Компании'", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void gotoProfileFromCompanies() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkProfileButtonVisible();
        companies().clickProfile();

        userEdit().checkUserInfoBlockVisible();
    }

    @TmsLink("751")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Переход в профиль из раздела 'Компании' (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void gotoProfileFromCompaniesB2C() {
        var user = UserManager.getQaUser();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(user);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToProfile();
        b2cShop().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        b2cShop().interactHeader().interactAccountMenu().clickToCompanies();

        b2cCompanies().checkProfileButtonVisible();
        b2cCompanies().clickProfile();

        b2cUserEdit().checkUserInfoBlockVisible();
    }

    @TmsLink("23")
    @Test(description = "Добавление новой компании из раздела 'Компании' / ввод корректного ИНН", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void addCompanyFromCompaniesPagePositive() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsEmpty();

        companies().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalVisible();
        companies().interactAddCompany().fillInn(company.getInn());
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().fillName(company.getJuridicalName());
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalNotVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().checkCompaniesCount(1);
        companies().checkCompaniesListContains(company.getJuridicalName());
    }

    @TmsLink("752")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Добавление новой компании из раздела 'Компании' / ввод корректного ИНН  (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void addCompanyFromCompaniesPagePositiveB2C() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(user);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cShop().interactHeader().clickToProfile();
        b2cShop().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        b2cShop().interactHeader().interactAccountMenu().clickToCompanies();

        b2cCompanies().checkCompaniesListIsEmpty();

        b2cCompanies().clickAddCompany();
        b2cCompanies().interactAddCompany().checkAddCompanyModalVisible();
        b2cCompanies().interactAddCompany().fillInn(company.getInn());
        b2cCompanies().interactAddCompany().clickAddCompany();
        b2cCompanies().interactAddCompany().fillName(company.getJuridicalName());
        b2cCompanies().interactAddCompany().clickAddCompany();
        b2cCompanies().interactAddCompany().checkAddCompanyModalNotVisible();

        b2cCompanies().goToPage();
        b2cCompanies().checkCompaniesListIsNotEmpty();
        b2cCompanies().checkCompaniesCount(1);
        b2cCompanies().checkCompaniesListContains(company.getJuridicalName());
    }

    @TmsLink("43")
    @Test(description = "Добавление новой компании из ЛК / ввод некорректного ИНН", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void addCompanyFromCompaniesPageIncorrectINN() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsEmpty();

        companies().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalVisible();
        companies().interactAddCompany().fillInn("000000000000");
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().checkInnInputErrorIsVisible();
        companies().interactAddCompany().checkErrorTextEquals("Введите корректный ИНН");

        companies().interactAddCompany().closeModal();
        companies().interactAddCompany().checkAddCompanyModalNotVisible();
        companies().checkCompaniesListIsEmpty();
    }

    @TmsLink("758")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Добавление новой компании из ЛК / ввод некорректного ИНН (B2c-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void addCompanyFromCompaniesPageIncorrectINNB2C() {
        var user = UserManager.getQaUser();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(user);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().checkCompaniesListIsEmpty();

        b2cCompanies().clickAddCompany();
        b2cCompanies().interactAddCompany().checkAddCompanyModalVisible();
        b2cCompanies().interactAddCompany().fillInn("000000000000");
        b2cCompanies().interactAddCompany().clickAddCompany();
        b2cCompanies().interactAddCompany().checkInnInputErrorIsVisible();
        b2cCompanies().interactAddCompany().checkErrorTextEquals("Введите корректный ИНН");

        b2cCompanies().interactAddCompany().closeModal();
        b2cCompanies().interactAddCompany().checkAddCompanyModalNotVisible();
        b2cCompanies().checkCompaniesListIsEmpty();
    }

    @TmsLink("37")
    @Test(description = "Отображение  блока 'менеджер' в кабинете компании", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyManagerInfo() {
        var userData = UserManager.getQaUser();
        var managerData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.addManagerForCompany(company.getInn(), managerData);

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();
        companyInfoPage().checkManagerInfoDisplayed();
        companyInfoPage().checkManagerInfoContainsText(managerData.getEmail());
    }

    @TmsLink("754")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Отображение  блока 'менеджер' в кабинете компании (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyManagerInfoB2C() {
        var userData = UserManager.getQaUser();
        var managerData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.addManagerForCompany(company.getInn(), managerData);

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().checkCompaniesListIsNotEmpty();
        b2cCompanies().clickOnFirstCompanyName();
        b2cCompanyInfo().checkCompanyInfoIsVisible();
        b2cCompanyInfo().checkManagerInfoDisplayed();
        b2cCompanyInfo().checkManagerInfoContainsText(managerData.getEmail());
    }

    @TmsLink("38")
    @Test(description = "Переход в профиль компании", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyInfo() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();
        companyInfoPage().checkLoadingSpinnerNotVisible();
        companyInfoPage().checkCompanyInfoContainsText(company.getJuridicalName());
    }

    @TmsLink("755")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Переход в профиль компании (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyInfoB2C() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().checkCompaniesListIsNotEmpty();
        b2cCompanies().clickOnFirstCompanyName();
        b2cCompanyInfo().checkCompanyInfoIsVisible();
        b2cCompanyInfo().checkLoadingSpinnerNotVisible();
        b2cCompanyInfo().checkCompanyInfoContainsText(company.getJuridicalName());
    }

    @TmsLink("39")
    @Test(description = "Кнопка 'обновления баланса' и 'подсказка'", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyBalance() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        var companyId = helper.getCompanyId(company.getInn());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();

        companyInfoPage().checkPaymentAccountAmountContainsText("0,00 ₽");
        companyInfoPage().hoverAccountAmountAdditionalInfo();
        companyInfoPage().checkPaymentAccountWarningDisplayed();
        companyInfoPage().clickAccountAmountRefreshButton();
        companyInfoPage().interactHeader().checkErrorAlertDisplayed();

        helper.addPaymentAccountForCompany(companyId, 1000);
        companyInfoPage().refresh();
        companyInfoPage().checkCompanyInfoIsVisible();

        companyInfoPage().checkPaymentAccountAmountContainsText("1 000,00 ₽");
        companyInfoPage().hoverAccountAmountRefreshButton();
        companyInfoPage().checkPaymentAccountUpdateInfoDisplayed();
        companyInfoPage().hoverAccountAmountAdditionalInfo();
        companyInfoPage().checkPaymentAccountWarningDisplayed();

        helper.setPaymentAccountBalance(companyId, -1000);
        companyInfoPage().refresh();
        companyInfoPage().checkCompanyInfoIsVisible();

        companyInfoPage().checkPaymentAccountAmountContainsText("-1 000,00 ₽");
        companyInfoPage().hoverAccountAmountRefreshButton();
        companyInfoPage().checkPaymentAccountUpdateInfoDisplayed();
        companyInfoPage().hoverAccountAmountAdditionalInfo();
        companyInfoPage().checkPaymentAccountWarningDisplayed();
    }

    @TmsLink("756")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Кнопка 'обновления баланса' и 'подсказка' (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testCompanyBalanceB2C() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        var companyId = helper.getCompanyId(company.getInn());

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(userData);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().checkCompaniesListIsNotEmpty();
        b2cCompanies().clickOnFirstCompanyName();
        b2cCompanyInfo().checkCompanyInfoIsVisible();

        b2cCompanyInfo().checkPaymentAccountAmountContainsText("0,00 ₽");
        b2cCompanyInfo().hoverAccountAmountAdditionalInfo();
        b2cCompanyInfo().checkPaymentAccountWarningDisplayed();
        b2cCompanyInfo().clickAccountAmountRefreshButton();
        b2cCompanyInfo().interactHeader().checkErrorAlertDisplayed();

        helper.addPaymentAccountForCompany(companyId, 1000);
        b2cCompanyInfo().refresh();
        b2cCompanyInfo().checkCompanyInfoIsVisible();

        b2cCompanyInfo().checkPaymentAccountAmountContainsText("1 000,00 ₽");
        b2cCompanyInfo().hoverAccountAmountRefreshButton();
        b2cCompanyInfo().checkPaymentAccountUpdateInfoDisplayed();
        b2cCompanyInfo().hoverAccountAmountAdditionalInfo();
        b2cCompanyInfo().checkPaymentAccountWarningDisplayed();

        helper.setPaymentAccountBalance(companyId, -1000);
        b2cCompanyInfo().refresh();
        b2cCompanyInfo().checkCompanyInfoIsVisible();

        b2cCompanyInfo().checkPaymentAccountAmountContainsText("-1 000,00 ₽");
        b2cCompanyInfo().hoverAccountAmountRefreshButton();
        b2cCompanyInfo().checkPaymentAccountUpdateInfoDisplayed();
        b2cCompanyInfo().hoverAccountAmountAdditionalInfo();
        b2cCompanyInfo().checkPaymentAccountWarningDisplayed();
    }

    @TmsLink("42")
    @Test(description = "Блок 'Код безопасности'", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testSecurityCodeBlock() {
        var company = JuridicalData.juridical();
        var companyHeadUser = UserManager.getQaUser();
        var otherUsers = UserManager.getQaUsers(10);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), companyHeadUser.getEmail());
        helper.addEmployeesForCompany(company.getInn(), otherUsers);

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(companyHeadUser);
        business().interactAuthModal().checkModalIsNotVisible();
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkSecurityBlockDisplayed();

        companyInfoPage().clickGoForwardButton();
        companyInfoPage().checkSecurityBlockDisplayed();

        business().interactHeader().clickToProfile();
        business().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        business().interactHeader().interactAccountMenu().clickToLogout();

        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(otherUsers.get(9));
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyUsersDisplayed();
        companyInfoPage().checkSecurityBlockNotDisplayed();

        companyInfoPage().clickGoForwardButton();
        companyInfoPage().checkCompanyUsersDisplayed();
        companyInfoPage().checkSecurityBlockNotDisplayed();
    }

    @TmsLink("757")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Блок 'Код безопасности' (B2C-витрина)", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void testSecurityCodeBlockB2C() {
        var company = JuridicalData.juridical();
        var companyHeadUser = UserManager.getQaUser();
        var otherUsers = UserManager.getQaUsers(10);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), companyHeadUser.getEmail());
        helper.addEmployeesForCompany(company.getInn(), otherUsers);

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(companyHeadUser);
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().clickOnFirstCompanyName();
        b2cCompanyInfo().checkSecurityBlockDisplayed();

        b2cCompanyInfo().clickGoForwardButton();
        b2cCompanyInfo().checkSecurityBlockDisplayed();

        b2cCompanyInfo().interactHeader().clickToProfile();
        b2cCompanyInfo().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        b2cCompanyInfo().interactHeader().interactAccountMenu().clickToLogout();
        b2cCompanyInfo().interactHeader().checkProfileButtonNotVisible();

        b2cShop().goToPage(DEFAULT_SID);
        b2cShop().interactHeader().clickToLogin();
        b2cShop().interactAuthModal().authViaPhone(otherUsers.get(9));
        b2cShop().interactHeader().checkProfileButtonVisible();

        b2cCompanies().goToPage();
        b2cCompanies().clickOnFirstCompanyName();
        b2cCompanyInfo().checkCompanyUsersDisplayed();
        b2cCompanyInfo().checkSecurityBlockNotDisplayed();

        b2cCompanyInfo().clickGoForwardButton();
        b2cCompanyInfo().checkCompanyUsersDisplayed();
        b2cCompanyInfo().checkSecurityBlockNotDisplayed();
    }
}
