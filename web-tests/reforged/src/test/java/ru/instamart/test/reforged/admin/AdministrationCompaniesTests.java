package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Company;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление компаниями пользователя")
public final class AdministrationCompaniesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(23)
    @Test(description = "Корректное отображение списка компаний пользователя", groups = {"acceptance", "regression"})
    public void testUserCompanyList() {
        var userData = UserManager.getQaUser();
        var inn = Generate.generateINN(10);
        var companyName = Generate.literalString(10);
        helper.addCompanyForUser(inn, companyName, userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(inn);
        companies().clickToSearch();

        companies().checkCompanyNameExist(companyName);
        companies().checkInnExist(inn);
    }

    @CaseId(24)
    @Test(description = "Редактирование компании пользователя", groups = {"acceptance", "regression"})
    public void testUserCompanyEdit() {
        var userData = UserManager.getQaUser();
        var inn = Generate.generateINN(10);
        var companyName = Generate.literalString(10);
        var newCompanyName = Generate.literalString(10);
        helper.addCompanyForUser(inn, companyName, userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(inn);
        companies().clickToSearch();

        companies().goToCompanyEdit(companyName);
        companies().changeCompanyName(newCompanyName);
        companies().fillInn(inn);
        companies().clickToSearch();
        companies().checkCompanyNameExist(newCompanyName);
    }

    @CaseId(25)
    @Test(description = "Добавление новой компании пользователя", groups = {"acceptance", "regression"})
    public void testAddUserCompany() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(10), Generate.literalString(10), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().clickToAddCompany();

        addCompanies().fillCompany(company);
        addCompanies().clickToSubmit();

        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompanyNameExist(company.getCompanyName());
        companies().checkInnExist(company.getInn());
    }
}
