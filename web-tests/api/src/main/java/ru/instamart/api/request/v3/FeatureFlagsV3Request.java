package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

import static ru.instamart.api.helper.ApiV3Helper.getApiClientTokenWithProd;

public class FeatureFlagsV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.FEATURE_FLAGS)
    public static Response GET() {
        return givenWithAuth(getApiClientTokenWithProd(ClientV3.METRO_MARKETPLACE))
                .get(ApiV3Endpoints.FEATURE_FLAGS);
    }

    @Step("{method} /" + ApiV3Endpoints.FEATURE_FLAG)
    public static Response GET(String featureName) {
        return givenWithAuth(getApiClientTokenWithProd(ClientV3.METRO_MARKETPLACE))
                .get(ApiV3Endpoints.FEATURE_FLAG, featureName);
    }
}
