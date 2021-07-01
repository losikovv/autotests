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
import ru.instamart.api.model.v1.b2b.ManagerV1;
import ru.instamart.api.model.v1.b2b.SalesContractV1;
import ru.instamart.api.model.v1.b2b.UserV1;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.request.v1.b2b.CompanyManagersV1Request;
import ru.instamart.api.request.v1.b2b.CompanySalesContractV1Request;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.api.response.v1.b2b.CompanyManagerV1Response;
import ru.instamart.api.response.v1.b2b.CompanySalesContractV1Response;
import ru.instamart.api.response.v1.b2b.CompanyV1Response;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.JuridicalData;
import ru.instamart.kraken.testdata.UserManager;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode422;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class CompaniesV1Tests extends RestBase {
    private final JuridicalData companyData = UserManager.juridical();
    private CompanyV1 company;
    private final ManagerV1 manager = new ManagerV1();
    private final SalesContractV1 salesContract = new SalesContractV1();

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

    @Story("Admin Web")
    @CaseId(650)
    @Test(description = "Добавление менеджера в компанию",
            groups = {"api-instamart-regress"})
    public void postCompanyManager() {
        UserV1 managerAsUser = new UserV1();
        managerAsUser.setId(101779);
        manager.setUser(managerAsUser);
        Response response = CompanyManagersV1Request.POST(company.getId(), manager);
        checkStatusCode200(response);
        ManagerV1 managerFromResponse = response.as(CompanyManagerV1Response.class).getManager();
        assertEquals(manager.getUser().getId(), managerFromResponse.getUser().getId());
        manager.setId(managerFromResponse.getId());
    }


    @Story("Admin Web")
    @CaseId(651)
    @Test(description = "Удаление менеджера из компании",
            groups = {"api-instamart-regress"},
    dependsOnMethods = "postCompanyManager")
    public void deleteCompanyManager() {
        Response response = CompanyManagersV1Request.DELETE(manager.getId());
        checkStatusCode200(response);
        Response responseForCheck = CompaniesV1Request.GET(company.getId());
        assertNull(responseForCheck.as(CompanyV1Response.class).getCompany().getManager());
    }


    @Story("Admin Web")
    @CaseId(652)
    @Test(description = "Добавление договора купли-продажи",
            groups = {"api-instamart-regress"})
    public void postSalesContract() {
        String number = company.getId().toString() + Generate.digitalString(5); // создаём уникальный номер
        salesContract.setNumber(Integer.valueOf(number));
        salesContract.setSigningDate(new SimpleDateFormat("yyyy-MM-dd").format(Instant.now().toEpochMilli()));
        Response response = CompanySalesContractV1Request.POST(company.getId(), salesContract.getNumber(), salesContract.getSigningDate());
        checkStatusCode200(response);
        SalesContractV1 salesContractResponse = response.as(CompanySalesContractV1Response.class).getSalesContract();
        assertTrue(salesContractResponse.getActive());
        salesContract.setId(salesContractResponse.getId());
    }

    @Story("Admin Web")
    @CaseId(653)
    @Test(description = "Ошибка при добавлении договора при активном договоре",
            groups = {"api-instamart-regress"},
    dependsOnMethods = "postSalesContract")
    public void postSalesContractWhenHasActiveContract() {
        Response response = CompanySalesContractV1Request.POST(company.getId(), salesContract.getNumber(), salesContract.getSigningDate());
        checkStatusCode422(response);
        assertEquals("У компании уже имеется активный договор",
                response.as(CompanySalesContractV1Response.class).getSalesContract().getErrors().getArchivedAt().get(0));
    }

    @Story("Admin Web")
    @CaseId(654)
    @Test(description = "Изменение даты подписания договора",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "postSalesContract")
    public void putSalesContractDate() {
        if (new SimpleDateFormat("MM-dd").format(Instant.now().toEpochMilli()).equals("01-01")){ //если сегодня первое января, то вчерашняя дата будет в прошлом году и нумерация обнулится. Это сломает логику следующих тестов
            salesContract.setSigningDate(new SimpleDateFormat("yyyy-MM-dd")
                    .format(Instant.now()
                            .plus(1, ChronoUnit.DAYS)
                            .toEpochMilli()));
        } else {
            salesContract.setSigningDate(new SimpleDateFormat("yyyy-MM-dd") // получаем вчерашнюю дату
                    .format(Instant.now()
                            .minus(1, ChronoUnit.DAYS)
                            .toEpochMilli()));
        }

        Response response = CompanySalesContractV1Request.PUT(company.getId(), salesContract);
        checkStatusCode200(response);
        assertEquals(salesContract.getSigningDate(),
                response.as(CompanySalesContractV1Response.class).getSalesContract().getSigningDate());
    }

    @Story("Admin Web")
    @CaseId(655)
    @Test(description = "Архивация договора купли-продажи",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"postSalesContractWhenHasActiveContract", "putSalesContractDate"})
    public void postArchiveSalesContract() {
        Response response = CompanySalesContractV1Request.POST(salesContract.getId());
        checkStatusCode200(response);
        assertFalse(response.as(CompanySalesContractV1Response.class).getSalesContract().getActive());
    }

    @Story("Admin Web")
    @CaseId(656)
    @Test(description = "Ошибка при добавлении договора с уже использованным в  текущем году номером",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "postArchiveSalesContract")
    public void postSalesContractNumberExisting() {
        Response response = CompanySalesContractV1Request.POST(company.getId(), salesContract.getNumber());
        checkStatusCode422(response);
        assertEquals("уже существует",
                response.as(CompanySalesContractV1Response.class).getSalesContract().getErrors().getNumber().get(0));
    }


}
