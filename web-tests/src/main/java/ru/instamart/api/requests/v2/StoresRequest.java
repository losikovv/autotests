package instamart.api.requests.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.endpoints.ApiV2EndPoints;
import instamart.core.service.MapperService;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

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
         * Получаем промоакции в магазине
         */
        @Step
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.Stores.PROMOTION_CARDS, sid);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class Store {

        private Double lat;
        private Double lon;
        @JsonProperty(value = "shipping_method")
        private String shippingMethod;
        @JsonProperty(value = "operational_zone_id")
        private Integer operationalZoneId;

        public Double getLat() {
            return lat;
        }

        public void setLat(final Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(final Double lon) {
            this.lon = lon;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public void setShippingMethod(final String shippingMethod) {
            this.shippingMethod = shippingMethod;
        }

        public Integer getOperationalZoneId() {
            return operationalZoneId;
        }

        public void setOperationalZoneId(final Integer operationalZoneId) {
            this.operationalZoneId = operationalZoneId;
        }
    }
}
