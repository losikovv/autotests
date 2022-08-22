package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.util.Map;
import java.util.Objects;

public final class StoresV2Request extends ApiV2RequestBase {

    /**
     * Получаем список доступных магазинов по координатам
     */
    @Step("{method} /" + ApiV2EndPoints.STORES)
    public static Response GET(final Store store) {
        final Map<String, Object> params = Mapper.INSTANCE.objectToMap(store);
        return givenWithSpec()
                .params(params)
                .get(ApiV2EndPoints.STORES);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.Stores.BY_SID)
    public static Response GET(int sid) {
        return givenWithSpec()
                .get(ApiV2EndPoints.Stores.BY_SID, sid);
    }

    public static class ForMap {
        @Step("{method} /" + ApiV2EndPoints.Stores.FOR_MAP)
        public static Response GET(ForMapParams params) {
            RequestSpecification requestSpecification = givenWithSpec();
            if (Objects.nonNull(params)) {
                requestSpecification.queryParams(Mapper.INSTANCE.objectToMap(params));
            }
            return requestSpecification.get(ApiV2EndPoints.Stores.FOR_MAP);
        }
    }

    public static class PromotionCards {
        /**
         * Получаем промо-акции в магазине
         */
        @Step("{method} /" + ApiV2EndPoints.Stores.PROMOTION_CARDS)
        public static Response GET(final int sid) {
            return givenWithSpec().get(ApiV2EndPoints.Stores.PROMOTION_CARDS, sid);
        }
    }

    public static class HealthCheck {

        @Step("{method} /" + ApiV2EndPoints.Stores.HEALTH_CHECK)
        public static Response GET(final int sid) {
            return givenWithSpec().get(ApiV2EndPoints.Stores.HEALTH_CHECK, sid);
        }
    }

    public static class NextDeliveries{

        @Step("{method} /" + ApiV2EndPoints.Stores.NEXT_DELIVERIES)
        public static Response GET(final int sid){
            return givenWithAuth()
                    .get(ApiV2EndPoints.Stores.NEXT_DELIVERIES, sid);
        }

        @Step("{method} /" + ApiV2EndPoints.Stores.NEXT_DELIVERIES)
        public static Response GET(final int sid, Map<String, String> params){
            return givenWithAuth()
                    .params(params)
                    .get(ApiV2EndPoints.Stores.NEXT_DELIVERIES, sid);
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static final class ForMapParams {
        private String bbox;
        @JsonProperty(value = "shipping_method")
        private String shippingMethod;
        @JsonProperty(value = "retailer_ids")
        private String retailerIds;
    }
}
