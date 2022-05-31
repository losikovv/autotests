package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

import static ru.instamart.api.helper.ApiV3Helper.getApiClientTokenWithProd;

@SuppressWarnings("unchecked")
public class StoresV3Request extends ApiV3RequestBase {

    public static class Stores {
        /**
         * Получение списка магазинов
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", 55.747581);
            requestParams.put("lon", 37.797110);
            return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .get(ApiV3Endpoints.STORES);
        }
    }

    public static class Delivery {
        /**
         * Получение списка магазинов на доставку
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", 55.747581);
            requestParams.put("lon", 37.797110);
            return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .queryParam("shipping_method", "delivery")
                    .get(ApiV3Endpoints.STORES);
        }
    }

    public static class PickupFromStore {
        /**
         * Получение списка магазинов на самовывоз
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", 55.747581);
            requestParams.put("lon", 37.797110);
            return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .queryParam("shipping_method", "pickup_from_store")
                    .get(ApiV3Endpoints.STORES);
        }
    }
    public static class ClosestShippingOptions {
        /**
         * Получение ближайших опций доставки из магазина
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", 55.747581);
            requestParams.put("lon", 37.797110);
            return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .queryParam("include", "closest_shipping_options")
                    .get(ApiV3Endpoints.STORES);
        }
    }
    public static class RetailerId {
        /**
         * Получение списка магазинов по ритейлеру Metro
         */
        @Step("{method} /" + ApiV3Endpoints.STORES)
        public static Response GET() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("lat", 55.747581);
            requestParams.put("lon", 37.797110);
            return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .queryParam("retailer_id", "metro")
                    .get(ApiV3Endpoints.STORES);
        }
    }
}