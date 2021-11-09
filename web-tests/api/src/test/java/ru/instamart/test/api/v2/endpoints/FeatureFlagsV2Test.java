package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.FeatureFlagsV2Request;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
public class FeatureFlagsV2Test extends RestBase {

    @CaseId(792)
    @Test(description = "Получение списка всех фича-флагов",
            groups = {"api-instamart-smoke"})
    public void getFeatureFlags200() {
        Response response = FeatureFlagsV2Request.GET();
        checkStatusCode200(response);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/api_v2/FeatureFlags.json"));
    }
}
