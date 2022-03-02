package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.*;

@Epic("SMBUSINESS UI")
@Feature("Раздел 'Компании' B2C и B2B")
public class CompaniesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(21)
    @Test(description = "Переход в раздел 'Компании'", groups = {"smoke", "regression"})
    public void gotoCompaniesFromHeader() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeader().checkProfileButtonVisible();

        business().interactHeader().clickToProfile();
        business().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        business().interactHeader().interactAccountMenu().clickToCompanies();

        companies().checkCompaniesListIsEmpty();
        companies().checkProfileButtonVisible();
        companies().checkAddCompanyButtonVisible();
    }

    @CaseId(750)
    @Test(description = "Переход в раздел 'Компании' (B2C-витрина)", groups = {"smoke", "regression"})
    public void gotoCompaniesFromHeaderB2C() {
        var user = UserManager.getQaUser();

        b2cShop().goToPageFromTenant();
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

    @CaseId(22)
    @Test(description = "Переход в профиль из раздела 'Компании'", groups = {"smoke", "regression"})
    public void gotoProfileFromCompanies() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeader().checkProfileButtonVisible();

        business().interactHeader().clickToProfile();
        business().interactHeader().interactAccountMenu().checkAccountMenuVisible();
        business().interactHeader().interactAccountMenu().clickToCompanies();

        companies().checkProfileButtonVisible();
        companies().clickProfile();

        userEdit().checkUserInfoBlockVisible();
    }

    @CaseId(751)
    @Test(description = "Переход в профиль из раздела 'Компании' (B2C-витрина)", groups = {"smoke", "regression"})
    public void gotoProfileFromCompaniesB2C() {
        var user = UserManager.getQaUser();

        b2cShop().goToPageFromTenant();
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

    @CaseId(23)
    @Test(description = "Добавление новой компании из раздела 'Компании' / ввод корректного ИНН", groups = {"smoke", "regression"})
    public void addCompanyFromCompaniesPagePositive() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeader().checkProfileButtonVisible();

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

    @CaseId(752)
    @Test(description = "Добавление новой компании из раздела 'Компании' / ввод корректного ИНН  (B2C-витрина)", groups = {"smoke", "regression"})
    public void addCompanyFromCompaniesPagePositiveB2C() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();

        b2cShop().goToPageFromTenant();
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

        b2cCompanies().goToPageFromTenant();
        b2cCompanies().checkCompaniesListIsNotEmpty();
        b2cCompanies().checkCompaniesCount(1);
        b2cCompanies().checkCompaniesListContains(company.getJuridicalName());
    }

    @CaseId(43)
    @Test(description = "Добавление новой компании из ЛК / ввод некорректного ИНН", groups = {"smoke", "regression"})
    public void addCompanyFromCompaniesPageIncorrectINN() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeader().checkProfileButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsEmpty();

        companies().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalVisible();
        companies().interactAddCompany().fillInn("000000000000");
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().checkInnInputErrorIsVisible();
        companies().interactAddCompany().checkErrorTextEquals("Введите корректный ИНН");

        companies().interactAddCompany().close();
        companies().interactAddCompany().checkAddCompanyModalNotVisible();
        companies().checkCompaniesListIsEmpty();
    }

    @CaseId(37)
    @Test(description = "Отображение  блока 'менеджер' в кабинете компании", groups = {"smoke", "regression"})
    public void testCompanyManagerInfo() {
        var userData = UserManager.getQaUser();
        var managerData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.addManagerForCompany(company.getInn(), managerData);

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeader().checkProfileButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();
        companyInfoPage().checkManagerInfoDisplayed();
        companyInfoPage().checkManagerInfoContainsText(managerData.getEmail());
    }

    @CaseId(38)
    @Test(description = "Переход в профиль компании", groups = {"smoke", "regression"})
    public void testCompanyInfo() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeader().checkProfileButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();
        companyInfoPage().checkCompanyInfoContainsText(company.getJuridicalName());
    }

    @CaseId(39)
    @Test(description = "Кнопка 'обновления баланса' и 'подсказка'", groups = {"smoke", "regression"})
    public void testCompanyBalance() {
        var company = JuridicalData.juridical();
        var userData = UserManager.getQaUser();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        var companyId = helper.getCompanyId(company.getInn());

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(userData);
        business().interactHeader().checkProfileButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyInfoIsVisible();

        companyInfoPage().checkPaymentAccountAmountContainsText("нет данных");
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

    @CaseId(42)
    @Test(description = "Блок 'Код безопасности'", groups = {"smoke", "regression"})
    public void testSecurityCodeBlock() {
        var company = JuridicalData.juridical();
        var companyHeadUser = UserManager.getQaUser();
        var otherUsers = UserManager.getQaUsers(10);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), companyHeadUser.getEmail());
        helper.addEmployeesForCompany(company.getInn(), otherUsers);

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(companyHeadUser);
        business().interactHeader().checkProfileButtonVisible();
        business().addCookie(CookieFactory.COOKIE_ALERT);

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
        business().interactHeader().checkProfileButtonVisible();

        companies().goToPage();
        companies().clickOnFirstCompanyName();
        companyInfoPage().checkCompanyUsersDisplayed();
        companyInfoPage().checkSecurityBlockNotDisplayed();

        companyInfoPage().clickGoForwardButton();
        companyInfoPage().checkCompanyUsersDisplayed();
        companyInfoPage().checkSecurityBlockNotDisplayed();
    }
}
