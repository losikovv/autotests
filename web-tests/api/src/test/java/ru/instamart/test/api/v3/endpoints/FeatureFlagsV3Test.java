package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.FeatureFlagV3;
import ru.instamart.api.request.v3.FeatureFlagsV3Request;
import ru.instamart.api.response.v3.FeatureFlagV3Response;
import ru.instamart.api.response.v3.FeatureFlagsV3Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Feature flags")
public class FeatureFlagsV3Test extends RestBase {
    private FeatureFlagV3 featureFlag;

    @CaseId(2604)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение списка фича-флагов")
    public void getFeatureFlags() {
        Response response = FeatureFlagsV3Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureFlagsV3Response.class);
        featureFlag = response.as(FeatureFlagsV3Response.class).getFeatureFlags().get(0);
    }

    @CaseId(2605)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение фича-флага по ключу",
            dependsOnMethods = "getFeatureFlags")
    public void getFeatureFlag() {
        Response response = FeatureFlagsV3Request.GET(featureFlag.getName());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureFlagV3Response.class);
    }
}
