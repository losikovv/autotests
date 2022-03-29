package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.CurrentTimeV2Request;
import ru.instamart.api.response.v2.CurrentTimeV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Серверное время")
public class CurrentTimeV2Test extends RestBase {

    @CaseId(2468)
    @Story("Текущее серверное время")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение текущего серверного времени")
    public void currentTimeTest() {
        final Response response = CurrentTimeV2Request.GET();
        checkStatusCode200(response);
        CurrentTimeV2Response currentTimeV2Response = response.as(CurrentTimeV2Response.class);
        assertNotNull(currentTimeV2Response,"Время пришло пустым");
    }

}
