package ru.instamart.api.request.v1.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.sbermarket.common.Mapper;

public final class ShipmentsAdminV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.SHIPMENTS)
    public static Response GET(final ShipmentsData shipmentsData) {
        return givenWithAuth()
                .queryParams(Mapper.INSTANCE.objectToMap(shipmentsData))
                .get(ApiV1Endpoints.Admin.SHIPMENTS);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @Builder
    public static final class ShipmentsData {
        private Integer page;
        @JsonProperty(value = "per_page")
        private Integer perPage;
        @JsonProperty(value = "search[completed_shipments]")
        private Boolean completedShipments;
        /**
         * online card=1
         * cash=2
         * business=3
         */
        @JsonProperty(value = "search[payment_method_id][]")
        private Integer paymentMethodId;
        @JsonProperty(value = "search[completed_at_st]")
        private String completedAtSt;
        @JsonProperty(value = "search[completed_at_end]")
        private String completedAtEnd;
        @JsonProperty(value = "search[only_b2b]")
        private Boolean onlyB2b;
        @JsonProperty(value = "search[operational_zone_id][]")
        private Long operationalZoneId;
        @JsonProperty(value = "search[payment_state][]")
        private String paymentState;
        @JsonProperty(value = "search[delivery_window_starts_at_st]")
        private String deliveryWindowStartsAtSt;
        @JsonProperty(value = "search[delivery_window_starts_at_end]")
        private String deliveryWindowStartsAtEnd;
        @JsonProperty(value = "search[total_weight_st]")
        private Double totalWeightSt;
        @JsonProperty(value = "search[total_weight_end]")
        private Double totalWeightEnd;
        @JsonProperty(value = "search[item_count_st]")
        private Integer itemCountSt;
        @JsonProperty(value = "search[item_count_end]")
        private Integer itemCountEnd;
        @JsonProperty(value = "search[retailer_id][]")
        private Long retailerId;
        @JsonProperty(value = "search[store_id][]")
        private Integer storeId;
        @JsonProperty(value = "search[combined_state][]")
        private String combinedState;
        @JsonProperty(value = "search[number]")
        private String orderNumber;
        @JsonProperty(value = "search[api_client_id][]")
        private Long apiClientId;
        @JsonProperty(value = "search[driver_login]")
        private String driverLogin;
        @JsonProperty(value = "search[shopper_login]")
        private String shopperLogin;
    }
}
