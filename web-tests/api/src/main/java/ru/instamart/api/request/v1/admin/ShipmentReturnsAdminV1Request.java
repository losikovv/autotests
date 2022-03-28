package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

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
}
