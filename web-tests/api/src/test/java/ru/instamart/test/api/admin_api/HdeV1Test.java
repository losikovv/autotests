package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.HdeV1Request;
import ru.instamart.api.response.v1.TicketsV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("HDE")
public class HdeV1Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2510)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение тикетов хелпдеска без параметров")
    public void getHdeTickets() {
        final Response response = HdeV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TicketsV1Response.class);
    }

    @CaseId(2513)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение тикетов хелпдеска по email")
    public void getHdeTicketsByEmail() {
        final Response response = HdeV1Request.GET(SessionFactory.getSession(SessionType.API_V1).getLogin(), "2");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TicketsV1Response.class);
        compareTwoObjects(response.as(TicketsV1Response.class).getMeta().getCurrentPage(), 2);
    }
}
