package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.data.Generate;

import java.io.File;

public class StoreZonesV1Request extends ApiV1RequestBase {

    public static class ZoneFiles {
        @Step("{method} /" + ApiV1Endpoints.Stores.ZONE_FILES)
        public static Response GET(Integer perPage) {
            return givenWithAuth()
                    .queryParam("per_page", perPage)
                    .get(ApiV1Endpoints.Stores.ZONE_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.ZONE_FILES)
        public static Response POST(Integer storeId, String filePath) {
            return givenWithAuth()
                    .multiPart("file", new File(filePath), "application/vnd.google-earth.kml+xml")
                    .post(ApiV1Endpoints.Stores.ZONE_FILES, storeId);
        }
    }

    public static class Zones {
        @Step("{method} /" + ApiV1Endpoints.Stores.ZONES)
        public static Response GET(final int sid) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Stores.ZONES, sid);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.ZONES)
        public static Response POST(Integer storeId, String area) {
            JSONObject body = new JSONObject();
            body.put("name", Generate.literalString(5));
            body.put("area", area);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Stores.ZONES, storeId);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.STORE_ZONES)
        public static Response PUT(Integer storeId, Integer zoneId, String zoneName, String area) {
            JSONObject body = new JSONObject();
            body.put("name", zoneName);
            body.put("area", area);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .put(ApiV1Endpoints.Stores.STORE_ZONES, storeId, zoneId);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.STORE_ZONES)
        public static Response DELETE(Integer storeId, Integer zoneId) {
            return givenWithAuth()
                    .delete(ApiV1Endpoints.Stores.STORE_ZONES, storeId, zoneId);
        }
    }
}
