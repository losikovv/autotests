package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.helper.ApiV3Helper.getApiClientToken;

public class AggregatingCategoriesV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES)
    public static Response GET(Integer sid) {
        String token = EnvironmentProperties.Env.isProduction() ? EnvironmentProperties.METRO_TOKEN : getApiClientToken(ClientV3.METRO_MARKETPLACE);
        return givenWithAuth(token)
                .get(ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES, sid);
    }
}
