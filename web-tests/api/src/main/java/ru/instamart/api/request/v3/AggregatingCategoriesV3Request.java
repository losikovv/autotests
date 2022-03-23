package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

public class AggregatingCategoriesV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES)
    public static Response GET(Integer sid) {
        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .get(ApiV3Endpoints.Stores.AGGREGATING_CATEGORIES, sid);
    }
}
