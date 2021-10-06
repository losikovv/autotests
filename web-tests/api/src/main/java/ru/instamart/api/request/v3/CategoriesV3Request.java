package ru.instamart.api.request.v3;

import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class CategoriesV3Request extends ApiV3RequestBase {

    public static Response GET(Integer sid) {
        return givenMetroMarketPlace()
                .get(ApiV3Endpoints.Stores.CATEGORIES, sid);
    }
}
