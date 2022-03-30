package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.admin.CurrenciesV1Request;
import ru.instamart.api.response.v1.CurrenciesV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Валюта")
public class CurrenciesV1Test extends RestBase {

    @CaseId(2475)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о валютах")
    public void getCurrencies() {
        admin.authApi();
        final Response response = CurrenciesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CurrenciesV1Response.class);
    }
}
