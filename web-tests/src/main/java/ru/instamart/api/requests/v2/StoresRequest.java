package ru.instamart.api.requests.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.core.service.MapperService;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.*;

import java.util.Map;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class StoresRequest {

    /**
     * Получаем список доступных магазинов по координатам
     */
    @Step("{method} /" + ApiV2EndPoints.Stores.COORDINATES)
    public static Response GET(final Store store) {
        final Map<String, Object> params = MapperService.INSTANCE.objectToMap(store);
        return givenCatch()
                .params(params)
                .get(ApiV2EndPoints.Stores.COORDINATES);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.Stores.SID)
    public static Response GET(int sid) {
        return givenCatch().get(ApiV2EndPoints.Stores.SID, sid);
    }

    public static class PromotionCards {
        /**
         * Получаем промо-акции в магазине
         */
        @Step("{method} /" + ApiV2EndPoints.Stores.PROMOTION_CARDS)
        public static Response GET(final int sid) {
            return givenCatch().get(ApiV2EndPoints.Stores.PROMOTION_CARDS, sid);
        }
    }

    public static class HealthCheck {

        @Step("{method} /" + ApiV2EndPoints.Stores.HEALTH_CHECK)
        public static Response GET(final int sid) {
            return givenCatch().get(ApiV2EndPoints.Stores.HEALTH_CHECK, sid);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static final class Store {

        private Double lat;
        private Double lon;
        @JsonProperty(value = "shipping_method")
        private String shippingMethod;
        @JsonProperty(value = "operational_zone_id")
        private Integer operationalZoneId;
    }
}
