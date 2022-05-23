package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Company;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление компаниями пользователя")
public final class AdministrationCompaniesTests {

    private final ApiHelper helper = new ApiHelper();

    //TODO переделать: по информации от Сергея Кулаковского, эти тесты относятся к /admin/users/${userId}/company_documents:
    //Пользователи -> Пользователь -> РЕКВИЗИТЫ КОМПАНИЙ
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

        companies().checkCompaniesVisible();
        companies().checkCompaniesCount(1);
        companies().checkFirstCompanyName(companyName);
        companies().checkFirstCompanyINN(inn);
    }

    //TODO переделать: по информации от Сергея Кулаковского, эти тесты относятся к /admin/users/${userId}/company_documents:
    //Пользователи -> Пользователь -> РЕКВИЗИТЫ КОМПАНИЙ
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

        companies().checkCompaniesVisible();
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().changeCompanyName(newCompanyName);

        companies().fillInn(inn);
        companies().clickToSearch();
        companies().checkCompaniesVisible();
        companies().checkFirstCompanyName(newCompanyName);
    }

    //TODO переделать: по информации от Сергея Кулаковского, эти тесты относятся к /admin/users/${userId}/company_documents:
    //Пользователи -> Пользователь -> РЕКВИЗИТЫ КОМПАНИЙ
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

        companies().checkCompaniesVisible();
        companies().checkCompaniesCount(1);
        companies().checkFirstCompanyName(company.getCompanyName());
        companies().checkFirstCompanyINN(company.getInn());
    }

    @CaseId(477)
    @Test(description = "Тест поиска компании по ИНН", groups = {"regression"})
    public void testSearchCompany() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(10), Generate.literalString(10), userData.getEmail());
        helper.addCompanyForUser(company.getInn(), company.getCompanyName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().checkCompaniesCount(1);
        companies().checkFirstCompanyName(company.getCompanyName());
        companies().checkFirstCompanyINN(company.getInn());
    }

    @CaseId(478)
    @Test(description = "Тест создание компании через админку (ИП)", groups = {"regression"})
    public void testAddUserCompanyIP() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(12), Generate.literalString(10), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().clickToAddCompany();

        addCompanies().fillCompany(company);
        addCompanies().clickToSubmit();

        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().checkCompaniesCount(1);
        companies().checkFirstCompanyName(company.getCompanyName());
        companies().checkFirstCompanyINN(company.getInn());
    }

    @CaseId(479)
    @Test(description = "Тест создание компании через админку (ЮЛ)", groups = {"regression"})
    public void testAddUserCompanyOOO() {
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

        companies().checkCompaniesVisible();
        companies().checkCompaniesCount(1);
        companies().checkFirstCompanyName(company.getCompanyName());
        companies().checkFirstCompanyINN(company.getInn());
    }

    @CaseId(480)
    @Test(description = "Тест добавление менеджера к компании", groups = {"regression"})
    public void testAddCompanyManager() {
        var managerData = UserManager.getQaUser();
        helper.addManagerRoleToUser(managerData);
        var userData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().checkCompanyManagersNotDisplayed();

        company().fillManagerEmail(managerData.getEmail());
        company().checkFoundedUsersDisplayed();
        company().selectFirstOfFoundedUsers();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Менеджер компании добавлен");

        company().checkCompanyManagersListSize(1);
    }

    @CaseId(481)
    @Test(description = "Тест удаление менеджера компании", groups = {"regression"})
    public void testDeleteCompanyManager() {
        var userData = UserManager.getQaUser();
        var managerData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());
        helper.addManagerForCompany(company.getInn(), managerData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().checkCompanyManagersDisplayed();
        company().checkCompanyManagersListSize(1);

        company().clickDeleteManager();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Удалить менеджера компании?");
        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Менеджер компании удален");

        company().checkCompanyManagersNotDisplayed();
    }

    @CaseId(482)
    @Test(description = "Тест добавление договора (с указанной датой)", groups = {"regression"})
    public void testAddContractOneStep() {
        var userData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        var contractNumber = Generate.digitalString(5);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkContractsListIsEmpty();
        company().clickAddContract();
        company().checkContractNumberInputVisible();
        company().fillContractNumber(contractNumber);
        company().clickDateInput();
        company().checkCalendarVisible();
        company().clickToday();
        company().clickSaveContract();

        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Документ будет отмечен как подписанный, вы уверены?");

        company().clickConfirmStatusModalYes();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Договор успешно создан!");
        company().checkNoticePopupNotDisplayed();

        company().checkContractsCount(1);
        company().checkContractNumber(contractNumber);
        company().checkContractDate(TimeUtil.getDateWithoutTimeViaSlash());

        company().clickAddContract();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Уже имеется активный договор");
    }

    @CaseId(482)
    @Test(description = "Тест добавление и редактирование договора", groups = {"regression"})
    public void testAddContractTwoStep() {
        var userData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        var contractNumber = Generate.digitalString(5);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkContractsListIsEmpty();
        company().clickAddContract();
        company().checkContractNumberInputVisible();
        company().fillContractNumber(contractNumber);
        company().clickSaveContract();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Договор успешно создан!");
        company().checkNoticePopupNotDisplayed();

        company().checkContractsCount(1);
        company().checkContractNumber(contractNumber);
        company().checkContractDateIsEmpty();

        company().clickAddContract();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Уже имеется активный договор");
        company().checkNoticePopupNotDisplayed();

        company().clickEditContract();
        company().checkContractNumberInputVisible();
        company().clickDateInput();
        company().checkCalendarVisible();
        company().clickToday();
        company().clickSaveContract();

        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Документ будет отмечен как подписанный, вы уверены?");

        company().clickConfirmStatusModalYes();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Договор успешно обновлен!");
        company().checkNoticePopupNotDisplayed();

        company().checkContractsCount(1);
        company().checkContractNumber(contractNumber);
        company().checkContractDate(TimeUtil.getDateWithoutTimeViaSlash());

        company().clickAddContract();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Уже имеется активный договор");
    }


    @CaseId(483)
    @Test(description = "Тест обновление баланса (положительный баланс)", groups = {"regression"})
    public void testBalanceRefreshPositiveBalance() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn("8733270179");
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkCurrentBalance("10 500,00 ₽");

        company().clickRefreshBalance();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Баланс обновлен");
        company().checkBalanceRefreshDateTime("Дата обновления:" + TimeUtil.getDateTimeViaSlash());
    }

    @CaseId(483)
    @Test(description = "Тест обновление баланса (отрицательный баланс)", groups = {"regression"})
    public void testBalanceRefreshNegativeBalance() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn("4727666224");
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkCurrentBalance("-10 500,00 ₽");

        company().clickRefreshBalance();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Баланс обновлен");
        company().checkBalanceRefreshDateTime("Дата обновления:" + TimeUtil.getDateTimeViaSlash());
    }

    @CaseId(484)
    @Test(description = "Тест обновление кода безопасности", groups = {"regression"})
    public void testSecurityCodeRefresh() {
        var userData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().clickRefreshSecurityCode();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Вы уверены, что хотите обновить код безопасности?");
        company().clickConfirmStatusModalOk();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Код безопасности обновлен");

        //TODO Добавить проверку отправленного письма после выполнения https://jira.sbmt.io/browse/INFRADEV-8877
    }

    @CaseId(485)
    @Test(description = "Тест работа кнопки 'Подтвердить компанию'", groups = {"regression"})
    public void testConfirmCompany() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(10), Generate.literalString(10), userData.getEmail());
        helper.addCompanyForUser(company.getInn(), company.getCompanyName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().checkFirstCompanyStatus("Требует одобрения");
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkConfirmStatusButtonDisplayed();
        company().clickConfirmStatus();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Подтвердить статус компании?");

        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Статус компании подтвержден");
        company().checkNoticePopupNotDisplayed();

        companies().clickToSearch();
        companies().checkCompaniesVisible();
        companies().checkFirstCompanyStatus("Одобрен");
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().checkConfirmStatusButtonDisplayed();
        company().clickConfirmStatus();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Отозвать статус компании?");

        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Статус компании отозван");

        companies().clickToSearch();
        companies().checkCompaniesVisible();
        companies().checkFirstCompanyStatus("Требует одобрения");
    }

    @CaseId(487)
    @Test(description = "Тест удаление представителя компании", groups = {"regression"})
    public void testDeleteCompanyEmployee() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(10), Generate.literalString(10), userData.getEmail());
        helper.addCompanyForUser(company.getInn(), company.getCompanyName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().checkCompanyEmployeesDisplayed();
        company().checkCompanyEmployeesListSize(1);

        company().clickDeleteEmployee();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Удалить представителя компании?");
        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Успех! Данные компании обновлены");

        company().checkCompanyEmployeesNotDisplayed();
    }

    @CaseId(488)
    @Test(description = "Тест подтверждение юзера", groups = {"regression"})
    public void testConfirmCompanyEmployee() {
        var userData = UserManager.getQaUser();
        var company = new Company(Generate.generateINN(10), Generate.literalString(10), userData.getEmail());
        helper.addCompanyForUser(company.getInn(), company.getCompanyName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();
        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().checkCompanyEmployeesDisplayed();
        company().checkCompanyEmployeesListSize(1);

        company().clickConfirmEmployee();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Подтвердить представителя компании?");
        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Успех! Данные компании обновлены");
        company().checkNoticePopupNotDisplayed();

        company().checkCompanyEmployeesListSize(1);

        company().clickConfirmEmployee();
        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Отозвать представителя компании?");
        company().clickConfirmStatusModalOk();
        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Успех! Данные компании обновлены");

        company().checkCompanyEmployeesListSize(1);
    }

    @CaseId(490)
    @Test(description = "Тест удаление договора", groups = {"regression"})
    public void testDeleteContract() {
        var userData = UserManager.getQaUser();
        var company = JuridicalData.juridical();
        var contractNumber = Generate.digitalString(5);
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), userData.getEmail());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        companies().goToPage();
        companies().fillInn(company.getInn());
        companies().clickToSearch();

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();

        company().clickAddContract();
        company().checkContractNumberInputVisible();
        company().fillContractNumber(contractNumber);
        company().clickDateInput();
        company().checkCalendarVisible();
        company().clickToday();
        company().clickSaveContract();

        company().checkConfirmActionModalDisplayed();
        company().clickConfirmStatusModalYes();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Договор успешно создан!");
        company().checkNoticePopupNotDisplayed();

        company().checkContractsCount(1);
        company().checkContractNotInArchive();

        company().clickEditContract();
        company().checkContractNumberInputVisible();
        company().clickArchiveContract();

        company().checkConfirmActionModalDisplayed();
        company().checkConfirmActionModalTextEquals("Отправить договор в архив?");
        company().clickConfirmStatusModalYes();

        company().checkNoticePopupDisplayed();
        company().checkNoticeTextEquals("Договор успешно заархивирован!");
        company().checkContractInArchive();

        company().clickAddContract();
        company().checkContractNumberInputVisible();
    }

    @CaseId(491)
    @Test(description = "Тест редактирование названия", groups = {"regression"})
    public void testCompanyEditName() {
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

        companies().checkCompaniesVisible();

        companies().goToFirstCompanyEdit();
        company().checkCompanyPageVisible();
        company().changeCompanyName(newCompanyName);

        companies().fillInn(inn);
        companies().clickToSearch();
        companies().checkCompaniesVisible();
        companies().checkFirstCompanyName(newCompanyName);
    }
}
