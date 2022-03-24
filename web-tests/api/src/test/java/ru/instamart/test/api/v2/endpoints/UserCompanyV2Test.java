package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.CompanyV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.CompanyPresenceV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.UserV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.CompaniesExistV2Response;
import ru.instamart.api.response.v2.CompanyV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.factory.SessionFactory.getSession;
import static ru.instamart.api.factory.SessionFactory.makeSession;

@Epic("ApiV2")
@Feature("Компании")
public class UserCompanyV2Test extends RestBase {

    private String inn = Generate.generateINN(10);
    private UserData userData;
    private CompanyV2 collect;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        makeSession(SessionType.API_V2);
        userData = getSession(SessionType.API_V2).getUserData();
        collect = apiV2.addCompany(inn, "ООО Тест");
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(2254)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить данные по уже добавленной компании с пустым ИНН")
    public void getCompanyPresence400() {
        final Response response = CompanyPresenceV2Request.GET("");
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'inn'");
    }

    @CaseId(2253)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить данные по уже добавленной компании с несуществующим ИНН")
    public void getCompanyPresenceNull200() {
        final Response response = CompanyPresenceV2Request.GET("1234567891");
        checkStatusCode200(response);
        CompanyV2Response companyV2Response = response.as(CompanyV2Response.class);
        assertNull(companyV2Response.getCompany(), "Данные компании не null");
    }

    @CaseId(2252)
    @Skip(onServer = Server.STAGING)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить данные по уже добавленной компании с существующим ИНН")
    public void getCompanyPresence200() {
        final Response response = CompanyPresenceV2Request.GET(inn);
        checkStatusCode200(response);
        CompanyV2Response companyV2Response = response.as(CompanyV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(companyV2Response.getCompany().getInn(), inn, "Введенный ИНН отличается");
        softAssert.assertEquals(companyV2Response.getCompany().getName(), "ООО Тест", "Введенное наименование отличается");
        softAssert.assertEquals(companyV2Response.getCompany().getOwnerName(), "Qa S.", "Owner name отличается от введенного");
        softAssert.assertAll();
    }

    @CaseId(2279)
    @Story("Привязка заказа к компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Привязка заказа к компании с существующим orderNumber")
    public void patchOrderCompany404() {
        String orderNumber = apiV2.getOpenOrder().getNumber();

        final Response response = OrdersV2Request.Company.PATCH(orderNumber, 0);
        checkStatusCode404(response);
        checkError(response, "Компания не существует");
    }

    @CaseId(2278)
    @Story("Привязка заказа к компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Привязка заказа к компании с существующим ID")
    public void patchOrderCompanyWithoutOrderNumber404() {
        final Response response = OrdersV2Request.Company.PATCH("failedOrderNumber", collect.getId());
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(2277)
    @Story("Привязка заказа к компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Привязка заказа к компании с существующим ID и orderNumber")
    public void patchOrderCompany200() {
        String orderNumber = apiV2.getOpenOrder().getNumber();

        final Response response = OrdersV2Request.Company.PATCH(orderNumber, collect.getId());
        OrderV2 orderV2 = response.as(OrderV2Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(orderV2.getNumber(), orderNumber, "Номер ордера отличается от введенного");
        softAssert.assertEquals(orderV2.getCompany().getInn(), inn, "Введенный инн отличается");
        softAssert.assertEquals(orderV2.getCompany().getName(), "ООО Тест", "Введенный инн отличается");
        softAssert.assertAll();
    }

    @CaseId(2344)
    @Story("Получить факт наличия компаний у пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить факт наличия компаний у пользователя с компанией")
    public void getCompaniesExist200() {
        final Response response = UserV2Request.Exist.GET();
        checkStatusCode200(response);
        assertTrue(response.as(CompaniesExistV2Response.class).isCompaniesExist(), "Компания отсутствует");
    }

    @CaseId(2341)
    @Story("Прикрепить пользователя к компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Прикрепить пользователя к компании с невалидными company_security_code")
    public void companyEmployees422() {
        final Response response = UserV2Request.CompanyEmployees.POST(inn, "code"+ Generate.string(10));
        checkStatusCode422(response);
        final SoftAssert softAssert = new SoftAssert();
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getCompanySecurityCode(), "Неправильный код безопасности", "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "company_security_code", "Невалидный тип ошибки");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Неправильный код безопасности", "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Неправильный код безопасности", "Невалидная ошибка");
        softAssert.assertAll();
    }

    @CaseId(2342)
    @Story("Прикрепить пользователя к компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Прикрепить пользователя к компании с невалидными inn")
    public void companyEmployees404() {
        final Response response = UserV2Request.CompanyEmployees.POST("notValidINN", "notValidCode");
        checkStatusCode404(response);
        checkError(response, "Компания не существует");
    }
}
