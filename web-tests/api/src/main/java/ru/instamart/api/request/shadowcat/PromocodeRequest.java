package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.model.shadowcat.Promocode;
import ru.instamart.api.request.ShadowcatRequestBase;

import static ru.instamart.kraken.util.TimeUtil.getZonedFutureDate;

public class PromocodeRequest extends ShadowcatRequestBase {
    public static class Promocodes {
        @Step("{method} /"+ ShadowcatEndpoints.PROMOCODES)
        public static Response GET(int promoId) {
            return givenWithAuth()
                    .queryParam("promotion_id", promoId)
                    .get(ShadowcatEndpoints.PROMOCODES);
        }

        @Step("{method} /"+ ShadowcatEndpoints.PROMOCODES)
        public static Response POST(int promoId) {
            var body = Promocode.builder()
                    .activeUntil(getZonedFutureDate(3L))
                    .length(2)
                    .usageLimit(1)
                    .promotionId(promoId)
                    .build();
            return givenWithAuth()
                    .body(body)
                    .post(ShadowcatEndpoints.PROMOCODES);
        }

        @Step("{method} /"+ ShadowcatEndpoints.Promocode.CHECK)
        public static Response GET(String promocode) {
            return givenWithAuth()
                    .queryParam("promocode", promocode)
                    .get(ShadowcatEndpoints.Promocode.CHECK, promocode);
        }

        @Step("{method} /"+ ShadowcatEndpoints.Promocode.BY_ID)
        public static Response PUT(int promocodeId, int promoId, String code) {
            var body = Promocode.builder()
                    .activeUntil(getZonedFutureDate(3L))
                    .usageLimit(0)
                    .length(0)
                    .code(code)
                    .promotionId(promoId)
                    .build();
            return givenWithAuth()
                    .queryParam("promocodeId", promocodeId)
                    .body(body)
                    .put(ShadowcatEndpoints.Promocode.BY_ID, promocodeId);
        }

        @Step("{method} /"+ ShadowcatEndpoints.Promocode.BY_ID)
        public static Response DELETE(int promocodeId) {
            return givenWithAuth()
                    .queryParam("promocodeId", promocodeId)
                    .delete(ShadowcatEndpoints.Promocode.BY_ID, promocodeId);
        }
    }
}
