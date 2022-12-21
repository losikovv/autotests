package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.model.shadowcat.Promotion;
import ru.instamart.api.request.ShadowcatRequestBase;

import static ru.instamart.api.helper.ShadowcatHelper.createPromotionBody;

public class PromotionRequest extends ShadowcatRequestBase {

    public static class Promotions {
        @Step("{method} /" + ShadowcatEndpoints.PROMO)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShadowcatEndpoints.PROMO);
        }

        @Step("{method} /" + ShadowcatEndpoints.PROMOACTION)
        public static Response GET(int promoId) {
            return givenWithAuth()
                    .get(ShadowcatEndpoints.PROMOACTION, promoId);
        }

        @Step("{method} /" + ShadowcatEndpoints.PROMO)
        public static Response POST() {
            return givenWithAuth()
                    .body(createPromotionBody())
                    .post(ShadowcatEndpoints.PROMO);
        }

        @Step("{method} /" + ShadowcatEndpoints.PROMOACTION)
        public static Response PUT(int id) {
            Promotion promo = createPromotionBody().toBuilder().description("After update").build();
            return givenWithAuth()
                    .body(promo)
                    .put(ShadowcatEndpoints.PROMOACTION, id);
        }

        @Step("{method} /" + ShadowcatEndpoints.PROMOACTION)
        public static Response DELETE(int id) {
            return givenWithAuth()
                    .queryParam("promoId", id)
                    .delete(ShadowcatEndpoints.PROMOACTION, id);
        }
    }
}
