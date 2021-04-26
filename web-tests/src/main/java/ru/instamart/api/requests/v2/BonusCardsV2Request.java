package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class BonusCardsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.BONUS_CARDS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.BONUS_CARDS);
    }

    @Step("{method} /" + ApiV2EndPoints.BonusCards.WITH_PARAMS)
    public static Response POST(final int bonusProgramId, final int bonusCardNumber) {
        return givenWithAuth()
                .post(ApiV2EndPoints.BonusCards.WITH_PARAMS, bonusProgramId, bonusCardNumber);
    }

    @Step("{method} /" + ApiV2EndPoints.BonusCards.BY_ID)
    public static Response DELETE(final int bonusCardId) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.BonusCards.BY_ID, bonusCardId);
    }
}
