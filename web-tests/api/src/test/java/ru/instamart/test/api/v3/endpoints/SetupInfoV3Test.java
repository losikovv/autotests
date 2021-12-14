package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v3.SetupInfoV3Request;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Магазины")
public class SetupInfoV3Test extends RestBase {

    @CaseId(670)
    @Test(groups = {"api-instamart-regress"}, description = "Справка об интеграции")
    public void getSetupInfo() {
        Response response = SetupInfoV3Request.GET();
        checkStatusCode200(response);
    }

    @CaseId(671)
    @Test(groups = {"api-instamart-regress"}, description = "Доступный магазины")
    public void getSetupInfoStores() {
        Response response = SetupInfoV3Request.Stores.GET();
        checkStatusCode200(response);
    }
}
