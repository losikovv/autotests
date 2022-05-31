package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v3.CurrentTimeV3Request;
import ru.instamart.api.response.v3.CurrentTimeV3Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Current server time")
public class CurrentTimeV3Test extends RestBase {

    @CaseId(2363)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение серверного времени")
    public void getCurrentTime()  {
        Response response = CurrentTimeV3Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CurrentTimeV3Response.class);
    }
}
