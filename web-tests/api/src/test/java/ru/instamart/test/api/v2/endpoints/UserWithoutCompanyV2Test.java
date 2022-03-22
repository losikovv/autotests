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
import ru.instamart.api.request.v2.UserV2Request;
import ru.instamart.api.response.v2.CompaniesExistV2Response;
import ru.instamart.api.response.v2.CompanyV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertFalse;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.factory.SessionFactory.makeSession;
import static ru.instamart.kraken.data.Generate.generateINN;
import static ru.instamart.kraken.data.Generate.string;

@Epic("ApiV2")
@Feature("Компании")
public class UserWithoutCompanyV2Test extends RestBase {

    private String inn = generateINN(10);

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        makeSession(SessionType.API_V2);
    }

    @CaseId(2344)
    @Story("Получить факт наличия компаний у пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить факт наличия компаний у пользователя без компании")
    public void getCompaniesExist200() {
        final Response response = UserV2Request.Exist.GET();
        checkStatusCode200(response);
        assertFalse(response.as(CompaniesExistV2Response.class).isCompaniesExist(), "Вернулось true");
    }

    @CaseId(2360)
    @Story("Создание")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание компании без")
    public void createCompany200() {
        String name = "Autotest_" + string(10);
        final Response response = UserV2Request.POST(inn, name);
        checkStatusCode200(response);
        CompanyV2Response companyV2Response = response.as(CompanyV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(companyV2Response.getCompany().getName(), name, "Наименование компании не совпадает с введенным");
        softAssert.assertEquals(companyV2Response.getCompany().getInn(), inn, "ИНН не совпадает с введенным");
        softAssert.assertAll();
    }

}