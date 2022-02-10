package ru.instamart.api.request.admin;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

public final class ShipmentsAdminRequest extends AdminRequestBase {

    public static Response GET(final ShipmentsData shipmentsData) {
        return givenWithAuth()
                .get(AdminEndpoints.SHIPMENTS, shipmentsData);
    }

    @Data
    @Builder
    public static final class ShipmentsData {

    }
}
