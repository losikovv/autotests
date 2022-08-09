package ru.instamart.api.request.shadowcat;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.endpoint.ShadowcatEndpoints;
import ru.instamart.api.model.shadowcat.Promocode;
import ru.instamart.api.request.ShadowcatRequestBase;

import static ru.instamart.kraken.util.TimeUtil.getZonedFutureDate;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class PromocodeRequest extends ShadowcatRequestBase {
    public static class Promocodes {
        @Step("{method} /"+ ShadowcatEndpoints.PROMOCODES)
        public static Response POST() {
            var body = Promocode.builder()
                    .activeUntil(getZonedFutureDate(3L))
                    .length(6)
                    .usageLimit(1)
                    .promotionId(12)
                    .build();
            return givenWithAuth()
                    .body(body)
                    .post(ShadowcatEndpoints.PROMOCODES);
        }
    }
}
