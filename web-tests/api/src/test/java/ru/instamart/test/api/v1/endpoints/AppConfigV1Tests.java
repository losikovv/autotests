package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.AppConfigV1Request;
import ru.instamart.api.response.v1.AppConfigV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Конфигурация")
public class AppConfigV1Tests extends RestBase {

    @CaseId(1435)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о конфигурации системы")
    public void getAppConfig() {
        final Response response = AppConfigV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AppConfigV1Response.class);
    }
}
