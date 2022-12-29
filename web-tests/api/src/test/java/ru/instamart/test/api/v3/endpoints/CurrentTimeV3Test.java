package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v3.CurrentTimeV3Request;
import ru.instamart.api.response.v3.CurrentTimeV3Response;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Current server time")
public class CurrentTimeV3Test extends RestBase {

    @TmsLink("2363")
    @Test(  groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v3"},
            description = "Получение серверного времени")
    public void getCurrentTime()  {
        Response response = CurrentTimeV3Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CurrentTimeV3Response.class);
    }
}
