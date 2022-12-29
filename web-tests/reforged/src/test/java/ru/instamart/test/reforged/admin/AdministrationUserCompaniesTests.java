package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление компаниями пользователя: Пользователи -> Пользователь -> РЕКВИЗИТЫ КОМПАНИЙ")
public final class AdministrationUserCompaniesTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("23")
    @Test(description = "Корректное отображение списка компаний пользователя", groups = REGRESSION_ADMIN)
    public void testUserCompanyList() {
        var userData = UserManager.getQaUser();
        var companyData = JuridicalData.juridical();
        helper.addCompanyDocuments(userData, companyData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clickCompanyDocuments();

        userCompanies().waitPageLoad();
        userCompanies().checkCompaniesListNotEmpty();
        userCompanies().checkCompaniesCount(1);
        userCompanies().checkCompanyNameInRow(1, companyData.getJuridicalName());
    }

    @TmsLink("24")
    @Test(description = "Редактирование компании пользователя", groups = REGRESSION_ADMIN)
    public void testUserCompanyEdit() {
        var userData = UserManager.getQaUser();
        var companyData = JuridicalData.juridical();
        helper.addCompanyDocuments(userData, companyData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clickCompanyDocuments();

        userCompanies().waitPageLoad();
        userCompanies().checkCompaniesListNotEmpty();
        userCompanies().checkCompanyNameInRow(1, companyData.getJuridicalName());
        userCompanies().clickEdit(1);

        editUserCompany().waitPageLoad();
        editUserCompany().checkJuridicalNameClicable();
        editUserCompany().clearJuridicalName();
        var newJuridicalName = "ООО \"Рога и Копыта\"";
        editUserCompany().fillJuridicalName(newJuridicalName);

        editUserCompany().fillConsigneeJuridicalName(companyData.getJuridicalName());
        editUserCompany().fillConsigneeJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().fillConsigneeKpp(companyData.getKpp());

        editUserCompany().clickSubmit();

        userCompanies().waitPageLoad();
        userCompanies().checkCompaniesCount(1);
        userCompanies().checkCompanyNameInRow(1, newJuridicalName);

        userCompanies().clickEdit(1);

        editUserCompany().waitPageLoad();
        editUserCompany().checkJuridicalNameClicable();
        editUserCompany().checkInn(companyData.getInn());
        editUserCompany().checkJuridicalName(newJuridicalName);
        editUserCompany().checkJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().checkKpp(companyData.getKpp());
        editUserCompany().checkConsigneeJuridicalName(companyData.getJuridicalName());
        editUserCompany().checkConsigneeJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().checkConsigneeKpp(companyData.getKpp());
        editUserCompany().checkBik(companyData.getBik());
        editUserCompany().checkCorrespondentAccount(companyData.getCorrespondentAccountNumber());
        editUserCompany().checkOperationalAccount(companyData.getAccountNumber());
        editUserCompany().checkBank(companyData.getBankName());
    }

    @Issue("DEVB2B-2814")
    @TmsLink("25")
    @Test(description = "Добавление новой компании пользователя", groups = REGRESSION_ADMIN)
    public void testAddUserCompany() {
        var userData = UserManager.getQaUser();
        var companyData = JuridicalData.juridical();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clickCompanyDocuments();

        userCompanies().waitPageLoad();
        userCompanies().checkCompaniesListIsEmpty();
        userCompanies().clickAddNewCompany();

        editUserCompany().waitPageLoad();
        editUserCompany().checkJuridicalNameClicable();
        editUserCompany().fillInn(companyData.getInn());
        editUserCompany().fillJuridicalName(companyData.getJuridicalName());
        editUserCompany().fillJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().fillKpp(companyData.getKpp());
        editUserCompany().fillConsigneeJuridicalName(companyData.getJuridicalName());
        editUserCompany().fillConsigneeJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().fillConsigneeKpp(companyData.getKpp());
        editUserCompany().fillBik(companyData.getBik());
        editUserCompany().fillCorrespondentAccount(companyData.getCorrespondentAccountNumber());
        editUserCompany().fillOperationalAccount(companyData.getAccountNumber());
        editUserCompany().fillBank(companyData.getBankName());

        editUserCompany().clickSubmit();

        userCompanies().waitPageLoad();
        //DEVB2B-2814
        userCompanies().checkCompaniesCount(1);
        userCompanies().checkCompanyNameInRow(1, companyData.getJuridicalName());

        userCompanies().clickEdit(1);

        editUserCompany().waitPageLoad();
        editUserCompany().checkJuridicalNameClicable();
        editUserCompany().checkInn(companyData.getInn());
        editUserCompany().checkJuridicalName(companyData.getJuridicalName());
        editUserCompany().checkJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().checkKpp(companyData.getKpp());
        editUserCompany().checkConsigneeJuridicalName(companyData.getJuridicalName());
        editUserCompany().checkConsigneeJuridicalAddress(companyData.getJuridicalAddress());
        editUserCompany().checkConsigneeKpp(companyData.getKpp());
        editUserCompany().checkBik(companyData.getBik());
        editUserCompany().checkCorrespondentAccount(companyData.getCorrespondentAccountNumber());
        editUserCompany().checkOperationalAccount(companyData.getAccountNumber());
        editUserCompany().checkBank(companyData.getBankName());
    }
}
