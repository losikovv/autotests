package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class StoresV3Request extends ApiV3RequestBase {

    public static class Stores {
        /**
         * Получение списка магазинов
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", "55.836721");
            requestParams.put("lon", "37.445940");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version", "3.0")
                    .header("Client-Token", "14cd5d341d768bd4926fc9f5ce262094")
                    .get(ApiV3Endpoints.STORES);
        }
    }

    public static class Delivery {
        /**
         * Получение списка магазинов на доставку
         */
        @Step("{method} /" + ApiV3Endpoints.Stores.DELIVERY)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", "55.836721");
            requestParams.put("lon", "37.445940");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version", "3.0")
                    .header("Client-Token", "14cd5d341d768bd4926fc9f5ce262094")
                    .get(ApiV3Endpoints.Stores.DELIVERY);
        }
    }

    public static class PickupFromStore {
        /**
         * Получение списка магазинов на самовывоз
         */
        @Step("{method} /" + ApiV3Endpoints.Stores.PICKUP_FROM_STORE)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", "55.836721");
            requestParams.put("lon", "37.445940");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version", "3.0")
                    .header("Client-Token", "14cd5d341d768bd4926fc9f5ce262094")
                    .get(ApiV3Endpoints.Stores.PICKUP_FROM_STORE);
        }
    }
}