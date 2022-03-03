package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class PromotionCardCategoriesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.PROMOTION_CARD_CATEGORIES)
    public static Response GET() {
        return givenWithSpec()
                .get(ApiV1Endpoints.PROMOTION_CARD_CATEGORIES);
    }
}
