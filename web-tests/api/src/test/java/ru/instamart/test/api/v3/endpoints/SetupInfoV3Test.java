package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v3.SetupInfoV3Request;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Магазины")
public class SetupInfoV3Test extends RestBase {

    @TmsLink("670")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Справка об интеграции")
    public void getSetupInfo() {
        Response response = SetupInfoV3Request.GET();
        checkStatusCode200(response);
    }

    @TmsLink("671")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Доступный магазины")
    public void getSetupInfoStores() {
        Response response = SetupInfoV3Request.Stores.GET();
        checkStatusCode200(response);
    }
}
