package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.api.enums.SessionProvider;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.v1.b2b.CompanyPresenceV1Request;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.request.v1.b2b.UserCompanyEmployeesV1Request;
import ru.instamart.api.response.v1.b2b.*;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class UserCompaniesV1Tests extends RestBase {

    private final Juridical companyData = JuridicalData.juridical();
    private CompanyV1 company;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authAdminApi();
        final Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode200(response);
        company = response.as(CompanyV1Response.class).getCompany();
    }

    @Story("Web")
    @CaseId(616)
    @Test(description = "Список компаний пользователя",
            groups = {"api-instamart-regress"})
    public void getUserCompanies() {
        final Response response = UserCompaniesV1Request.GET();
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(CompaniesV1Response.class).getCompanies(), "список компаний");
        assertTrue(response.as(CompaniesV1Response.class).getCompanies().contains(company), "Наименование компании отличается от заданной");
    }

    @Story("Web")
    @CaseId(617)
    @Test(description = "Компания пользователя",
            groups = {"api-instamart-regress"})
    public void getCompanyByID(){
        final Response response = UserCompaniesV1Request.GET(company.getId().toString());
        checkStatusCode200(response);
        assertEquals(company.getId(), response.as(CompanyV1Response.class).getCompany().getId(), "id компании не совпадает с запрошенным");
    }

    @Story("Web")
    @CaseId(618)
    @Test(description = "Персональный менеджер компании",
            groups = {"api-instamart-regress"})
    public void getCompanyWithoutManager(){
        final Response response = UserCompaniesV1Request.Manager.GET(company.getId().toString());
        checkStatusCode200(response);
        assertNull(response.as(CompanyManagerV1Response.class).getManager(), "manager вернулся не пустой");
    }

    @Story("Web")
    @CaseId(619)
    @Test(description = "Список сотрудников компании",
            groups = {"api-instamart-regress"})
    public void getCompanyEmployees() {
        final Response response = UserCompaniesV1Request.Employees.GET(company.getId().toString());
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(EmployeesV1Response.class).getEmployees(), "список сотрудников компании");
    }

    @Story("Web")
    @CaseId(620)
    @Test(description = "Баланс  компании",
            groups = {"api-instamart-regress"})
    public void getPaymentAccount() {
        final Response response = UserCompaniesV1Request.PaymentAccount.GET(company.getId().toString());
        checkStatusCode200(response);
        assertNull(response.as(PaymentAccountV1Response.class).getPaymentAccount(), "payment_account not null");
    }

    @Story("Web")
    @CaseId(621)
    @Test(description = "Обновление баланса  компании",
            groups = {"api-instamart-regress"})
    public void postRefreshPaymentAccountError() {
        final Response response = UserCompaniesV1Request.PaymentAccount.POST(company.getId().toString());
        checkStatusCode422(response);
        checkFieldIsNotEmpty(response.as(PaymentAccountV1Response.class).getPaymentAccount().getErrors().getExternalPaymentAccount(),
                "ошибка добавления баланса");
    }

    @Story("Web")
    @CaseId(622)
    @Test(description = "Ошибка при повторной регистрации компании",
            groups = {"api-instamart-regress"})
    public void postCompanyRegistrationError(){
        final Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode422(response);
        checkFieldIsNotEmpty(response.as(CompanyV1Response.class).getCompany().getErrors().getInn(),
                "ошибка регистрации компании");
    }

    @Story("Web")
    @CaseId(623)
    @Test(description = "Статус регистрации компании (зарегистрирована)",
            groups = {"api-instamart-regress"})
    public void getCompanyPresence() {
        final Response response = CompanyPresenceV1Request.GET(company.getInn());
        checkStatusCode200(response);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.as(CompanyV1Response.class).getCompany().getInn(), company.getInn(), "ИНН  регистрации компании отличается от заданного");
        softAssert.assertEquals(response.as(CompanyV1Response.class).getCompany().getName(), company.getName(), "Наименование компании отличается от заданного");
        softAssert.assertAll();
    }

    @AfterClass(alwaysRun = true)
    public void postconditions() {
        final Response response = UserCompanyEmployeesV1Request.DELETE(company.getId().toString());
        checkStatusCode200(response);
    }
}
