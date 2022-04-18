package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.v3.AppConfigurationsV3Request;
import ru.instamart.api.response.v3.AppConfigurationCompanyV3Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV3")
@Feature("Конфигурация приложения")
public class AppConfigurationsV3Test extends RestBase {

    @CaseId(2361)
    @Test(  groups = {"api-instamart-smoke"},
            description = "Получение настроек организации")
    public void getCompanyDetails200() {
        Response response = AppConfigurationsV3Request.CompanyDetails.GET(ClientV3.METRO_MARKETPLACE);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AppConfigurationCompanyV3Response.class);
    }

    @CaseId(2362)
    @Test(  groups = {"api-instamart-smoke"},
            description = "Получение настроек организации без авторизации")
    public void getCompanyDetails401() {
        Response response = AppConfigurationsV3Request.CompanyDetails.GET(null);
        checkStatusCode401(response);
    }
}
