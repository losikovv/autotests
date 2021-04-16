package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class PromotionsV2Request extends ApiV2RequestBase {

    public static class PromoProducts {
        @Step("{method} /" + ApiV2EndPoints.Promotions.PROMO_PRODUCTS)
        public static Response GET(final int promoId, final int sid) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Promotions.PROMO_PRODUCTS, promoId, sid);
        }
    }

    public static class ReferralProgram {
        /**
         * Получаем инфу о реферальной программе
         */
        @Step("{method} /" + ApiV2EndPoints.Promotions.REFERRAL_PROGRAM)
        public static Response GET() {
            return givenWithSpec().get(ApiV2EndPoints.Promotions.REFERRAL_PROGRAM);
        }
    }
}
