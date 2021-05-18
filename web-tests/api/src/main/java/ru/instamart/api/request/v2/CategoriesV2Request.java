package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class CategoriesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.CATEGORIES)
    public static Response GET(final int sid) {
        return givenWithSpec()
                .get(ApiV2EndPoints.CATEGORIES, sid);
    }
}
