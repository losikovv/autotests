package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.request.ShadowcatRequestBase;

import static ru.instamart.kraken.util.TimeUtil.getZonedDate;
import static ru.instamart.kraken.util.TimeUtil.getZonedFutureDate;

public class PromotionRequest extends ShadowcatRequestBase {
    private static final String startDate = getZonedDate();
    private static final String finalDate = getZonedFutureDate(3L);

    public static class Promotions {
        @Step("{method} /"+ ShadowcatEndpoints.PROMO)
        public static Response GET() {
            return givenWithAuth().get(ShadowcatEndpoints.PROMO);
        }

        @Step("{method} /"+ ShadowcatEndpoints.PROMOACTION)
        public static Response GET(int promoId) {
            return givenWithAuth().get(ShadowcatEndpoints.PROMOACTION, promoId);
        }
    }
}
