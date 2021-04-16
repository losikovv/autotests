package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class PromotionsV2Request {

    @Step("{method} /" + ApiV2EndPoints.Promotions.REFERRAL_PROGRAM)
    public static Response GET(final int promoId, final int sid) {
        return givenCatch()
                .get(ApiV2EndPoints.Promotions.PROMO_PRODUCTS, promoId, sid);
    }

    public static class ReferralProgram {
        /**
         * Получаем инфу о реферальной программе
         */
        @Step("{method} /" + ApiV2EndPoints.Promotions.REFERRAL_PROGRAM)
        public static Response GET() {
            return givenCatch().get(ApiV2EndPoints.Promotions.REFERRAL_PROGRAM);
        }
    }
}
