package ru.instamart.api.request.v1.admin;

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

    @Data
    @Builder
    public static final class ShipmentsData {
        private int page;
        @JsonProperty(value = "per_page")
        private int perPage;
        @JsonProperty(value = "completed_shipments")
        private boolean completedShipments;
        /**
         * online card=1
         * cash=2
         * business=3
         */
        private int paymentMethodId;
    }
}
