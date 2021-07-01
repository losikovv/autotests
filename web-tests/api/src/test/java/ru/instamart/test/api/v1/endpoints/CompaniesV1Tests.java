package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.api.response.v1.b2b.CompanyV1Response;
import ru.instamart.kraken.testdata.JuridicalData;
import ru.instamart.kraken.testdata.UserManager;

import static org.junit.Assert.assertEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class CompaniesV1Tests extends RestBase {
    private final JuridicalData companyData = UserManager.juridical();
    private CompanyV1 company;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode200(response);
        company = response.as(CompanyV1Response.class).getCompany();
    }

    @Story("Admin Web")
    @CaseId(629)
    @Test(description = "Поиск компании по ИНН",
            groups = {"api-instamart-regress"})
    public void getCompaniesByINN() {
        Response response = CompaniesV1Request.GET(company.getInn());
        checkStatusCode200(response);
        assertEquals(company.getInn(), response.as(CompaniesV1Response.class).getCompanies().get(0).getInn());
    }

    @Story("Admin Web")
    @CaseId(630)
    @Test(description = "Переход на страницу компании",
            groups = {"api-instamart-regress"})
    public void getCompaniesByID() {
        Response response = CompaniesV1Request.GET(company.getId());
        checkStatusCode200(response);
        assertEquals(company.getId(), response.as(CompanyV1Response.class).getCompany().getId());
    }

    @Story("Admin Web")
    @CaseId(631)
    @Test(description = "Смена наименования компании",
            groups = {"api-instamart-regress"})
    public void putCompanyName() {
        CompanyV1 companyName = new CompanyV1().setName(UserManager.juridical().getJuridicalName());

        Response response = CompaniesV1Request.PUT(company.getId(), companyName);
        checkStatusCode200(response);
        assertEquals(companyName.getName(), response.as(CompanyV1Response.class).getCompany().getName());
    }

    @Story("Admin Web")
    @CaseId(632)
    @Test(description = "Добавление комментария менеджера",
            groups = {"api-instamart-regress"})
    public void putManagerComment() {
        CompanyV1 companyComment = new CompanyV1().setManagerComment("Test");

        Response response = CompaniesV1Request.PUT(company.getId(), companyComment);
        checkStatusCode200(response);
        assertEquals(companyComment.getManagerComment(), response.as(CompanyV1Response.class).getCompany().getManagerComment());
    }

    @Story("Admin Web")
    @CaseId(633)
    @Test(description = "Смена ссылки на компанию в CRM",
            groups = {"api-instamart-regress"})
    public void putLinkToCrm() {
        CompanyV1 companyLink = new CompanyV1().setLinkToCrm("https://test2.ru");

        Response response = CompaniesV1Request.PUT(company.getId(), companyLink);
        checkStatusCode200(response);
        assertEquals(companyLink.getLinkToCrm(), response.as(CompanyV1Response.class).getCompany().getLinkToCrm());
    }

    @Story("Admin Web")
    @CaseId(634)
    @Test(description = "Добавление постоплаты в качества способа оплаты",
            groups = {"api-instamart-regress"})
    public void putPostpay() {
        CompanyV1 companyPostpay = new CompanyV1().setPostpay(true);

        Response response = CompaniesV1Request.PUT(company.getId(), companyPostpay);
        checkStatusCode200(response);
        assertEquals(companyPostpay.getPostpay(), response.as(CompanyV1Response.class).getCompany().getPostpay());
    }

    @Story("Admin Web")
    @CaseId(635)
    @Test(description = "Добавление предоплаты в качества способа оплаты",
            groups = {"api-instamart-regress"})
    public void putPrepay() {
        CompanyV1 companyPrepay = new CompanyV1().setPrepay(true);

        Response response = CompaniesV1Request.PUT(company.getId(), companyPrepay);
        checkStatusCode200(response);
        assertEquals(companyPrepay.getPrepay(), response.as(CompanyV1Response.class).getCompany().getPrepay());
    }

    @Story("Admin Web")
    @CaseId(636)
    @Test(description = "Добавление оплаты по депозиту в качества способа оплаты",
            groups = {"api-instamart-regress"})
    public void putDeposit() {
        CompanyV1 companyDeposit = new CompanyV1().setDeposit(true);

        Response response = CompaniesV1Request.PUT(company.getId(), companyDeposit);
        checkStatusCode200(response);
        assertEquals(companyDeposit.getDeposit(), response.as(CompanyV1Response.class).getCompany().getDeposit());
    }


}
