package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.FeatureFlagsV2Request;
import ru.instamart.api.response.v2.FeatureFlagsV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Feature флаги")
public class FeatureFlagsV2Test extends RestBase {

    @CaseId(792)
    @Test(description = "Получение списка всех фича-флагов",
            groups = {"api-instamart-smoke"})
    public void getFeatureFlags200() {
        final Response response = FeatureFlagsV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureFlagsV2Response.class);
    }
}
