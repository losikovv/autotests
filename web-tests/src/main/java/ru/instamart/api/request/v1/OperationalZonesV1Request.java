package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class OperationalZonesV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.OPERATIONAL_ZONES)
    public static Response GET() {
        return givenWithSpec().get(ApiV1Endpoints.OPERATIONAL_ZONES);
    }

    @Step("{method} /" + ApiV1Endpoints.OperationalZones.ID)
    public static Response GET(int operationalZoneId) {
        return givenWithSpec().get(ApiV1Endpoints.OperationalZones.ID, operationalZoneId);
    }
}
