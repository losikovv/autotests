package ru.instamart.api.request.surge;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.SurgeEndpoints;
import ru.instamart.api.request.SurgeRequestBase;

public class SurgeRequest extends SurgeRequestBase {

    public static class Stores {

        @Step("{method} /" + SurgeEndpoints.STORES)
        public static Response GET(String storeUuid) {
            return givenWithAuth()
                    .get(SurgeEndpoints.STORES, storeUuid);
        }

        @Step("{method} /" + SurgeEndpoints.STORES)
        public static Response PUT(String storeUuid, Object hdmEnabled, String token) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("hdmEnabled", hdmEnabled);
            return givenWithAuth(token)
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .put(SurgeEndpoints.STORES, storeUuid);
        }

        public static Response PUT(String storeUuid, Object hdmEnabled) {
            return PUT(storeUuid, hdmEnabled, null);
        }

        @Step("{method} /" + SurgeEndpoints.STORES)
        public static Response POST(String storeUuid, Object hdmEnabled) {
            JSONObject postRequestParams = new JSONObject();
            postRequestParams.put("hdmEnabled", hdmEnabled);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(postRequestParams)
                    .post(SurgeEndpoints.STORES, storeUuid);
        }
    }
}
