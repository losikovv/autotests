package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.FeatureFlagV2;
import ru.instamart.api.request.v2.FeatureFlagsV2Request;
import ru.instamart.api.response.v2.FeatureFlagsV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Feature флаги")
public class FeatureFlagsV2Test extends RestBase {

    @CaseId(792)
    @Test(description = "Получение списка всех фича-флагов",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getFeatureFlags200() {
        final Response response = FeatureFlagsV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureFlagsV2Response.class);
    }

    @CaseId(792)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            dataProvider = "getAllFeatureFlags",
            dataProviderClass = RestDataProvider.class,
            description = "Получение существующего фича-флага")
    public void getFeatureFlagsItem200(FeatureFlagV2 featureFlagsV2) {
        final Response response = FeatureFlagsV2Request.FeatureFlags.GET(featureFlagsV2.getName());
        checkStatusCode200(response);
    }

    @CaseId(792)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получение несуществующего фича-флага")
    public void getFeatureFlagsItem404() {
        final Response response = FeatureFlagsV2Request.FeatureFlags.GET("failedName");
        checkStatusCode(response, 404, ContentType.JSON);
        checkError(response, "ActiveRecord::RecordNotFound");
    }
}
