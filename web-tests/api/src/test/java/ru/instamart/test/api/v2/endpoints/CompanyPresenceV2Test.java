package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.request.v2.CompanyPresenceV2Request;
import ru.instamart.api.response.v2.CompanyV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;
import static ru.instamart.api.factory.SessionFactory.createSessionToken;
import static ru.instamart.kraken.data.user.UserManager.getDefaultApiUser;

@Epic("ApiV2")
@Feature("Компании")
public class CompanyPresenceV2Test extends RestBase {

    private String inn = "2453252070";

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        createSessionToken(SessionType.API_V2, getDefaultApiUser());
    }

    @CaseId(2254)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить данные по уже добавленной компании с пустым ИНН")
    public void getCompanyPresence400() {
        Response response = CompanyPresenceV2Request.GET("");
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'inn'");
    }

    @CaseId(2253)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить данные по уже добавленной компании с несуществующим ИНН")
    public void getCompanyPresenceNull200() {
        Response response = CompanyPresenceV2Request.GET("1234567891");
        checkStatusCode200(response);
        CompanyV2Response companyV2Response = response.as(CompanyV2Response.class);
        assertNull(companyV2Response.getCompany(), "Даные компании не null");
    }

    @CaseId(2252)
    @Story("Получить данные по уже добавленной компании")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получить данные по уже добавленной компании с существующим ИНН")
    public void getCompanyPresence200() {
        Response response = CompanyPresenceV2Request.GET(inn);
        checkStatusCode200(response);
        CompanyV2Response companyV2Response = response.as(CompanyV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(companyV2Response.getCompany().getInn(), inn, "Введенный ИНН отличается");
        softAssert.assertEquals(companyV2Response.getCompany().getName(),"ООО Тест", "Введенное наименование отличается" );
        softAssert.assertEquals(companyV2Response.getCompany().getOwnerName(), "Test T.", "Owner name отличается от введенного");
        softAssert.assertAll();
    }

}
