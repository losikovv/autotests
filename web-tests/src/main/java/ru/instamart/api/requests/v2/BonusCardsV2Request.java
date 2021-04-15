package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class BonusCardsV2Request {

    @Step("{method} /" + ApiV2EndPoints.BONUS_CARDS)
    public static Response GET() {
        return givenWithAuthApiV2()
                .get(ApiV2EndPoints.BONUS_CARDS);
    }
}
