package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.io.File;

public class StoreZonesV1Request extends ApiV1RequestBase {

    public static class ZoneFiles {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.ZoneFiles.ZONE_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Stores.StoreId.ZoneFiles.ZONE_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.ZoneFiles.ZONE_FILES)
        public static Response POST(String filePath, Integer storeId) {
            return givenWithAuth()
                    .multiPart("file", new File(filePath), "application/vnd.google-earth.kml+xml")
                    .post(ApiV1Endpoints.Stores.StoreId.ZoneFiles.ZONE_FILES, storeId);
        }
    }

    public static class Zones {
        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.Zones.ZONES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Stores.StoreId.Zones.ZONES);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.StoreId.Zones.ZONES)
        public static Response POST(Long storeId, String area) {
            JSONObject body = new JSONObject();
            body.put("name", "polygon");
            body.put("area", area);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Stores.StoreId.Zones.ZONES, storeId);
        }
    }
}
