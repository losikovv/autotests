package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v3.SetupInfoV3Request;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Доступные магазины ритейлеру")

public class SetupInfoV3Test extends RestBase {

    @CaseId(670)
    @Test(groups = {"api-instamart-smoke"}, description = "Справка об интеграции")
    public void getSetupInfo() {
        Response response = SetupInfoV3Request.GET();
        checkStatusCode200(response);
    }

    @CaseId(671)
    @Test(groups = {"api-instamart-smoke"}, description = "Доступный магазины")
    public void getSetupInfoStores() {
        Response response = SetupInfoV3Request.Stores.GET();
        checkStatusCode200(response);
    }
}
