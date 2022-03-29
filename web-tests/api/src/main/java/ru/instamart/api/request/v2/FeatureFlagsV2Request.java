package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class FeatureFlagsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.FEATURE_FLAGS)
    public static Response GET() {
        return givenWithSpec()
                .get(ApiV2EndPoints.FEATURE_FLAGS);
    }

    public static class FeatureFlags {
        public static Response GET(String flag) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.FeatureFlags.BY_NAME, flag);
        }
    }
}
