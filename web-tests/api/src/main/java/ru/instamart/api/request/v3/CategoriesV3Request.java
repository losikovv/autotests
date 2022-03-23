package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

public class CategoriesV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.CATEGORIES)
    public static Response GET(Integer sid) {
        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .get(ApiV3Endpoints.Stores.CATEGORIES, sid);
    }

    @Step("{method} /" + ApiV3Endpoints.Stores.CATEGORY)
    public static Response GET(Integer sid, Long categoryId) {
        return givenWithAuth(ClientV3.METRO_MARKETPLACE)
                .get(ApiV3Endpoints.Stores.CATEGORY, sid, categoryId);
    }
}
