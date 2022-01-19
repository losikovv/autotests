package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class OperationalZonesV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.OPERATIONAL_ZONES)
    public static Response GET() {
        return givenWithSpec().get(ApiV1Endpoints.OPERATIONAL_ZONES);
    }

    @Step("{method} /" + ApiV1Endpoints.OperationalZones.ID)
    public static Response GET(int operationalZoneId) {
        return givenWithSpec().get(ApiV1Endpoints.OperationalZones.ID, operationalZoneId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.OPERATIONAL_ZONES)
    public static Response POST(String zoneName) {
        JSONObject body = new JSONObject();
        JSONObject operationalZone = new JSONObject();
        if (Objects.nonNull(zoneName)) operationalZone.put("name", zoneName);
        body.put("operational_zone", operationalZone);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.OPERATIONAL_ZONES);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.OPERATIONAL_ZONES)
    public static Response PUT(Integer operationalZoneID, String zoneName) {
        JSONObject body = new JSONObject();
        JSONObject operationalZone = new JSONObject();
        if (Objects.nonNull(zoneName)) operationalZone.put("name", zoneName);
        body.put("operational_zone", operationalZone);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.Admin.OperationalZones.BY_ID, operationalZoneID);
    }
}
