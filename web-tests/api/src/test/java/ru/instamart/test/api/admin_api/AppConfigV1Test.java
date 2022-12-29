package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.AppConfigV1Request;
import ru.instamart.api.response.v1.AppConfigV1Response;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Конфигурация")
public class AppConfigV1Test extends RestBase {

    @TmsLink("1435")
    @Story("Конфигурация системы")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о конфигурации системы")
    public void getAppConfig() {
        final Response response = AppConfigV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AppConfigV1Response.class);
    }
}
