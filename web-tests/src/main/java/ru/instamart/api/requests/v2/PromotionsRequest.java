package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class PromotionsRequest {

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
