package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.sbermarket.common.Mapper;

public final class ShipmentsAdminRequest extends AdminRequestBase {

    public static Response GET(final ShipmentsData shipmentsData) {
        return givenWithAuth()
                .queryParams(Mapper.INSTANCE.objectToMap(shipmentsData))
                .get(AdminEndpoints.SHIPMENTS);
    }

    @Data
    @Builder
    public static final class ShipmentsData {
        private int page;
        @JsonProperty(value = "per_page")
        private int perPage;
    }
}
