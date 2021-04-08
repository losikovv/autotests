package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class CategoriesV2Request {

    @Step("{method} /" + ApiV2EndPoints.CATEGORIES)
    public static Response GET(final int sid) {
        return givenCatch()
                .get(ApiV2EndPoints.CATEGORIES, sid);
    }
}
