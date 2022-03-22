package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class AggregatingCategoriesV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES)
    public static Response GET(Integer sid) {
        return givenMetroMarketPlace()
                .get(ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES, sid);
    }
}
