package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.request.ApiV3RequestBase;

@SuppressWarnings("unchecked")
public class OrderOptionsV3Request extends ApiV3RequestBase {

    public static class PickupFromStore {
        /**
         * Применение опций заказа для самовывоза
         */
        @Step("{method} /" + ApiV3Endpoints.OrderOptions.PICKUP_FROM_STORE)
        public static Response PUT (ApiV3TestData testData) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("retailer_id", testData.getRetailer_id());
            requestParams.put("store_id", testData.getStoreId());
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", testData.getItemId());
            itemParams.put("quantity", testData.getItemQuantity());
            itemParams.put("price", testData.getItemPrice());
            itemParams.put("discount", testData.getItemDiscount());
            itemParams.put("promo_total", testData.getItemPromoTotal());
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token",testData.getClientToken())
                    .put(ApiV3Endpoints.OrderOptions.PICKUP_FROM_STORE);
        }
    }

    public static class Delivery {
        /**
         * Получение списка опций для заказа с доставкой
         */
        @Step("{method} /" + ApiV3Endpoints.OrderOptions.DELIVERY)
        public static Response PUT (ApiV3TestData testData) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            JSONObject locationParams = new JSONObject();
            requestParams.put("retailer_id", testData.getRetailer_id());
            requestParams.put("location", locationParams);
            requestParams.put("items", items);
            items.add(itemParams);
            locationParams.put("lon", 37.797110);
            locationParams.put("lat", 55.747581);
            itemParams.put("id", testData.getItemId());
            itemParams.put("quantity", testData.getItemQuantity());
            itemParams.put("price", testData.getItemPrice());
            itemParams.put("discount", testData.getItemDiscount());
            itemParams.put("promo_total", testData.getItemPromoTotal());
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token", testData.getClientToken())
                    .put(ApiV3Endpoints.OrderOptions.DELIVERY);
        }
    }
}




