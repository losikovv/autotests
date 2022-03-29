package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.FeatureFlagV2;
import ru.instamart.api.request.v2.FeatureFlagsV2Request;
import ru.instamart.api.response.v2.FeatureFlagV2Response;
import ru.instamart.api.response.v2.FeatureFlagsV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Feature флаги")
public class FeatureFlagsV2Test extends RestBase {

    @CaseId(792)
    @Test(description = "Получение списка всех фича-флагов",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getFeatureFlags200() {
        final Response response = FeatureFlagsV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureFlagsV2Response.class);
    }

    @CaseId(792)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "getAllFeatureFlags",
            dataProviderClass = RestDataProvider.class,
            description = "Получение существующего фича-флага")
    public void getFeatureFlagsItem200(FeatureFlagV2 featureFlagsV2) {
        final Response response = FeatureFlagsV2Request.FeatureFlags.GET(featureFlagsV2.getName());
        checkStatusCode200(response);
        FeatureFlagV2Response featureFlagV2 = response.as(FeatureFlagV2Response.class);
        assertEquals(featureFlagV2.getFeatureFlag().getName(), featureFlagsV2.getName(), "Наименование фича-флага не совпадает");
        assertEquals(featureFlagV2.getFeatureFlag().getEnabled(), featureFlagsV2.getEnabled(), "enabled фича-флага не совпадает");
    }

    @CaseId(792)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего фича-флага")
    public void getFeatureFlagsItem404() {
        final Response response = FeatureFlagsV2Request.FeatureFlags.GET("failedName");
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }

}
