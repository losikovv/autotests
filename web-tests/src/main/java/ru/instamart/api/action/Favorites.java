package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Favorites {

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response GET(final String token, final int sid) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }
}
