package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.v1.b2b.*;
import ru.instamart.api.response.v1.b2b.*;
import ru.instamart.kraken.testdata.JuridicalData;
import ru.instamart.kraken.testdata.UserManager;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode422;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class UserCompaniesV1Tests extends RestBase {

    private final JuridicalData companyData = UserManager.juridical();
    private CompanyV1 company;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
            SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
            Response response = UserCompaniesV1Request.POST(companyData);
            company = response.as(CompanyV1Response.class).getCompany();
    }

    @CaseId(616)
    @Test(description = "Список компаний пользователя",
            groups = {"api-instamart-regress"})
    public void getUserCompanies() {
        Response response = UserCompaniesV1Request.GET();
        checkStatusCode200(response);
        assertFalse(response.as(CompaniesV1Response.class).getCompanies().isEmpty());
        assertTrue(response.as(CompaniesV1Response.class).getCompanies().contains(company));
    }

    @CaseId(617)
    @Test(description = "Компания пользователя",
            groups = {"api-instamart-regress"})
    public void getCompanyByID(){
        Response response = UserCompaniesV1Request.GET(company.getId().toString());
        checkStatusCode200(response);
        assertEquals(company.getId(), response.as(CompanyV1Response.class).getCompany().getId(), "id компании не совпадает с запрошенным");
    }

    @CaseId(618)
    @Test(description = "Персональный менеджер компании",
            groups = {"api-instamart-regress"})
    public void getCompanyWithoutManager(){
        Response response = UserCompaniesV1Request.Manager.GET(company.getId().toString());
        checkStatusCode200(response);
        assertNull(response.as(CompanyManagerV1Response.class).getManager());
    }

    @CaseId(619)
    @Test(description = "Список сотрудников компании",
            groups = {"api-instamart-regress"})
    public void getCompanyEmployees() {
        Response response = UserCompaniesV1Request.Employees.GET(company.getId().toString());
        checkStatusCode200(response);
        assertFalse(response.as(EmployeesV1Response.class).getEmployees().isEmpty());
    }

    @CaseId(620)
    @Test(description = "Баланс  компании",
            groups = {"api-instamart-regress"})
    public void getPaymentAccount() {
        Response response = UserCompaniesV1Request.PaymentAccount.GET(company.getId().toString());
        checkStatusCode200(response);
        assertNull(response.as(PaymentAccountV1Response.class).getPaymentAccount());
    }

    @CaseId(621)
    @Test(description = "Обновление баланса  компании",
            groups = {"api-instamart-regress"})
    public void postRefreshPaymentAccountError() {
        Response response = UserCompaniesV1Request.PaymentAccount.POST(company.getId().toString());
        checkStatusCode422(response);
        assertFalse(response.as(PaymentAccountV1Response.class).getPaymentAccount().getErrors().getExternalPaymentAccount().isEmpty());
    }

    @CaseId(622)
    @Test(description = "Ошибка при повторной регистрации компании",
            groups = {"api-instamart-regress"})
    public void postCompanyRegistrationError(){
        Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode422(response);
        assertFalse(response.as(CompanyV1Response.class).getCompany().getErrors().getInn().isEmpty());
    }

    @CaseId(623)
    @Test(description = "Статус регистрации компании (зарегистрирована)",
            groups = {"api-instamart-regress"})
    public void getCompanyPresence() {
        Response response = CompanyPresenceV1Request.GET(company.getInn());
        checkStatusCode200(response);
        assertEquals(response.as(CompanyV1Response.class).getCompany().getInn(), company.getInn());
        assertEquals(response.as(CompanyV1Response.class).getCompany().getName(), company.getName());
    }
}
