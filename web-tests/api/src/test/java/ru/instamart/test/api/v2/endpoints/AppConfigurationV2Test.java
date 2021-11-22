package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.AppConfigurationV2Request;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
public class AppConfigurationV2Test extends RestBase {

    @CaseId(789)
    @Test(description = "Получение конфигурации приложения",
            groups = {"api-instamart-smoke"})
    public void getAppConfiguration200() {
        final Response response = AppConfigurationV2Request.GET();

        checkStatusCode200(response);
    }
}
