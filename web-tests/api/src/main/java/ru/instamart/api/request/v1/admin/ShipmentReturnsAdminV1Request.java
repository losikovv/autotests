package ru.instamart.api.request.v1.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.util.List;

public class ShipmentReturnsAdminV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURNS)
    public static Response GET(String shipmentUUID){
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURNS, shipmentUUID);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURN)
    public static Response GET(String shipmentUUID, String shipmentReturnUUID){
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURN, shipmentUUID, shipmentReturnUUID);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURNS)
    public static Response POST(String shipmentUUID, ShipmentReturnRequest shipmentReturnBody){
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(Mapper.INSTANCE.objectToString(shipmentReturnBody))
                .post(ApiV1Endpoints.Admin.Shipments.SHIPMENT_RETURNS, shipmentUUID);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ShipmentReturnRequest {
        @JsonProperty("shipment_return")
        private ShipmentReturn shipmentReturn;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ShipmentReturn {
        private String comment;
        private String reason;
        private String kind;
        private String email;
        private Double amount;
        @JsonProperty("through_employee")
        private Boolean throughEmployee;
        @JsonProperty("item_returns")
        @Singular
        private List<ItemReturn> itemReturns;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ItemReturn {
        private Integer position;
        @JsonProperty("line_item_uuid")
        private String lineItemUuid;
        private Integer quantity;
        private Integer percent;
        private Double amount;
        private String kind;

    }
}
