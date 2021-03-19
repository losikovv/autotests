package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class CategoriesRequest {

    @Step("{method} /" + ApiV2EndPoints.CATEGORIES)
    public static Response GET(final int sid) {
        return givenCatch()
                .get(ApiV2EndPoints.CATEGORIES, sid);
    }
}
